package com.ym.gulimall.product.web;

import com.ym.gulimall.product.entity.CategoryEntity;
import com.ym.gulimall.product.service.CategoryService;
import com.ym.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // TODO 1 查出所有的1级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        // 视图解析器进行拼串
        // classpath:/templates/ + result + .html
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    // index/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() throws InterruptedException {
        return categoryService.getCatalogJson();
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {

        RLock lock = redisson.getLock("my-lock");

//        lock.lock();
        lock.lock(30, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行业务" + Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("锁释放" + Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    @ResponseBody
    @GetMapping("/write")
    public String writeValue() {

        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
            System.out.println("写锁加锁成功" + Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("写锁释放" + Thread.currentThread().getId());
        }
        return s;
    }

    @ResponseBody
    @GetMapping("/read")
    public String readValue() {

        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.readLock();
        rLock.lock();
        String s = "";
        try {
            System.out.println("读锁加锁成功" + Thread.currentThread().getId());
            s = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("读锁释放" + Thread.currentThread().getId());
        }
        return s;
    }
}
