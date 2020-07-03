package com.ym.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ym.gulimall.product.entity.BrandEntity;
import com.ym.gulimall.product.service.BrandService;
import com.ym.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
