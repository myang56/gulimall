    package com.ym.gulimall.product;

    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.ym.gulimall.product.entity.BrandEntity;
    import com.ym.gulimall.product.service.BrandService;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import java.util.List;

    /**
     *  1. 引入oss starter
     *  2. 配置key endpointer等相关信息
     *  3. 使用oss client进行相关操作
     */

    @SpringBootTest
    class GulimallProductApplicationTests {

        @Autowired
        BrandService brandService;



        @Test
        void contextLoads() {
    //        BrandEntity brandEntity = new BrandEntity();
    //        brandEntity.setName("huawei");
    //        brandService.save(brandEntity);
            List<BrandEntity> list =  brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
            list.forEach((item) -> {
                System.out.println(item);
            });


            System.out.println("save success");
        }

    }
