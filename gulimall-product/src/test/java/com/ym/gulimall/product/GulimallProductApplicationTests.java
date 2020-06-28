    package com.ym.gulimall.product;


    import com.aliyun.oss.OSSClient;
    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.ym.gulimall.product.entity.BrandEntity;
    import com.ym.gulimall.product.service.BrandService;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;

    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.InputStream;
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

        @Autowired
        OSSClient ossClient;

        @Test
        public void testUpload() throws FileNotFoundException {
//
//            // Endpoint以杭州为例，其它Region请按实际情况填写。
//            String endpoint = "oss-us-west-1.aliyuncs.com";
//            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//            String accessKeyId = "LTAI4G3CXbTYNYksN67ygbSA";
//            String accessKeySecret = "dJc1XzbudyDi7GcWChjNxkz6zDBVtM";

//           // 创建OSSClient实例。
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = new FileInputStream("/Users/miao/desktop/uwm1.jpeg");
            ossClient.putObject("gulimall-wa", "uwm1.jpeg", inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            System.out.println("upload success!");
        }

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
