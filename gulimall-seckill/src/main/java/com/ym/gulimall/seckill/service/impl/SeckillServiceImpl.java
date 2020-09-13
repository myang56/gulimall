package com.ym.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ym.common.to.mq.SeckillOrderTo;
import com.ym.common.utils.R;
import com.ym.common.vo.MemberRespVo;
import com.ym.gulimall.seckill.feign.CouponFeignService;
import com.ym.gulimall.seckill.feign.ProductFeignService;
import com.ym.gulimall.seckill.interceptor.LoginUserInterceptor;
import com.ym.gulimall.seckill.service.SeckillService;
import com.ym.gulimall.seckill.to.SecKillSkuRedisTo;
import com.ym.gulimall.seckill.vo.SeckillSessionsWithSkus;
import com.ym.gulimall.seckill.vo.SeckillSkuVo;
import com.ym.gulimall.seckill.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";


    @Override
    public void uploadSeckillSkuLatest3Days() {

        // 扫描需要参与秒杀的活动
        R session = couponFeignService.getLatest3DaySession();
        if (session.getCode() == 0) {
            // 上架商品
            List<SeckillSessionsWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });

            // save to redis
            // 1. 缓存活动信息
            saveSessionInfos(sessionData);
            // 2. 缓存活动的关联商品信息
            saveSessionSkuInfos(sessionData);
        }

    }

    /**
     * 返回当前可以参与的秒杀商品信息
     *
     * @return
     */
    @Override
    public List<SecKillSkuRedisTo> getCurrentSeckillSkus() {

        // 1. 确定当前时间属于哪个秒杀场次
        // 1970 -
        long time = new Date().getTime();
        Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
        for (String key : keys) {
            //
            String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
            String[] s = replace.split("_");
            Long start = Long.parseLong(s[0]);
            Long end = Long.parseLong(s[1]);

            if (time >= start && time <= end) {
                // 2. 获取当前这个秒杀场次需要的所有的商品信息

                List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                List<String> list = hashOps.multiGet(range);
                if (list != null) {
                    List<SecKillSkuRedisTo> collect = list.stream().map(item -> {
                        SecKillSkuRedisTo redisTo = JSON.parseObject((String) item, SecKillSkuRedisTo.class);
//                        redisTo.setRandomCode(null);
                        return redisTo;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }
        // 2.
        return null;
    }

    @Override
    public SecKillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SecKillSkuRedisTo skuRedisTo = JSON.parseObject(json, SecKillSkuRedisTo.class);

                    // 随机码
                    long current = new Date().getTime();

                    if (current >= skuRedisTo.getStartTime() && current <= skuRedisTo.getEndTime()) {

                    } else {
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
            }
        }
        return null;
    }


    /**
     * TODO 上架秒杀商品时 每一个数据都应该设置过期时间
     * 秒杀后续的流程 简化了收获地址等信息
     *
     * @param killId
     * @param key
     * @param num
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num) {

        long s1 = System.currentTimeMillis();
        MemberRespVo member = LoginUserInterceptor.loginUser.get();
        // 获取当前秒杀商品的详细信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        String json = hashOps.get(killId);
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            // 校验随机码与商品ID
            SecKillSkuRedisTo redisTo = JSON.parseObject(json, SecKillSkuRedisTo.class);
            Long startTime = redisTo.getStartTime();
            Long endTime = redisTo.getEndTime();
            long ttl = endTime - startTime;
            long time = new Date().getTime();
            if (time >= startTime && time <= endTime) {
                // 如果在秒杀时间内
                String randomCode = redisTo.getRandomCode();
                String skuId = redisTo.getPromotionSessionId() + "_" + redisTo.getSkuId();
                if (randomCode.equals(key) && killId.equals(skuId)) {
                    // 验证秒杀数量是否合理
                    log.info("LIMIT", redisTo.getSeckillLimit());
                    if (num <= redisTo.getSeckillLimit()) {
                        // 验证当前用户是否已参与过秒杀，只要秒杀成功就去 redis 占位 数据格式 userId_sessionId_skuId
                        String redisKey = member.getId() + "_" + skuId;
                        Boolean isHold = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        if (isHold) {
                            // 占位（placeholder）成功说明之前未参与过秒杀活动
                            // 引入分布式信号量
                            RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                            boolean b = semaphore.tryAcquire(num);
                            if (b) {
                                // 秒杀成功 快速下单 发送消息到 MQ 整个操作时间在 10ms 左右
                                SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                                String timeId = IdWorker.getTimeId();
                                seckillOrderTo.setOrderSn(timeId);
                                seckillOrderTo.setMemberId(member.getId());
                                seckillOrderTo.setNum(num);
                                seckillOrderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                                seckillOrderTo.setSkuId(redisTo.getSkuId());
                                seckillOrderTo.setSeckillPrice(redisTo.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", seckillOrderTo);
                                long s2 = System.currentTimeMillis();
                                log.info("耗时...", s2 - s1);
                                return timeId;
                            }
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                // 秒杀时间已过
                return null;
            }
        }
        return null;
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions) {
        sessions.stream().forEach(session -> {
            Long startTime = session.getStartTime().getTime();
            Long endTime = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
            Boolean hasKey = redisTemplate.hasKey(key);

            if (!hasKey) {
                List<String> collect = session.getRelationSkus().stream().map(item -> item.getPromotionSessionId().toString() + "_" + item.getSkuId().toString()).collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key, collect);
            }
        });

    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions) {

        sessions.stream().forEach(session -> {
            // prepare hash
            BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                // 4. 随机码
                String token = UUID.randomUUID().toString().replace("_", "");
                if (!ops.hasKey(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString())) {

                    // cache prod
                    SecKillSkuRedisTo redisTo = new SecKillSkuRedisTo();
                    // sku basic
                    R skuInfo = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                    if (skuInfo.getCode() == 0) {
                        SkuInfoVo info = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        redisTo.setSkuInfo(info);
                    }

                    log.info("seckill limit is ", seckillSkuVo.getSeckillLimit());

                    // sku seckillinfo
                    BeanUtils.copyProperties(seckillSkuVo, redisTo);

                    // 3.time
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());

                    // 4. 随机码
                    redisTo.setRandomCode(token);

                    String jsonString = JSON.toJSONString(redisTo);
                    ops.put(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString(), jsonString);

                    // 如果当前这个场次的商品的库存信息已经上架就不需要上架
                    // 5. 使用库存作为分布式的信号量 限流
                    String key = SKU_STOCK_SEMAPHORE + token;
                    RSemaphore semaphore = redissonClient.getSemaphore(key);
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }
            });
        });
    }
}
