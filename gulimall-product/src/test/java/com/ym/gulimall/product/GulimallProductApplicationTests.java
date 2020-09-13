package com.ym.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ym.gulimall.product.dao.AttrGroupDao;
import com.ym.gulimall.product.dao.SkuSaleAttrValueDao;
import com.ym.gulimall.product.entity.BrandEntity;
import com.ym.gulimall.product.service.BrandService;
import com.ym.gulimall.product.service.CategoryService;
import com.ym.gulimall.product.vo.SKuItemSaleAttrVo;
import com.ym.gulimall.product.vo.SpuItemAttrGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * 1. 引入oss starter
 * 2. 配置key endpointer等相关信息
 * 3. 使用oss client进行相关操作
 */

@Slf4j
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void test() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(5L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
//        List<SKuItemSaleAttrVo> salesAttrBySpuId = skuSaleAttrValueDao.getSalesAttrBySpuId(6L);
//        System.out.println(salesAttrBySpuId);
    }


    @Test
    public void testRedission() {

        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // save
        ops.set("hello", "word_" + UUID.randomUUID().toString());

        // search
        String hello = ops.get("hello");
        System.out.println("saved data is" + hello);
    }


    @Test
    public void testFindPath() {
        Long[] paths = categoryService.findCatelogPath(225L);
        log.info("完整路径:{}", Arrays.asList(paths));
    }


    @Test
    void contextLoads() {
        //        BrandEntity brandEntity = new BrandEntity();
        //        brandEntity.setName("huawei");
        //        brandService.save(brandEntity);
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        list.forEach((item) -> {
            System.out.println(item);
        });


        System.out.println("save success");
    }

}
