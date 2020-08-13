package com.ym.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  1. 整合MyBatis-Plus
 *     1. 导入依赖
 *       <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.3.2</version>
 *         </dependency>
 *      2. 配置
 *         1. 配置数据源
 *             1.导入数据库驱动
 *             2. 在application.yml 配置数据相关信息
 *         2. 配置Mybatis-plus
 *            1. 使用@MapperScan
 *            2. 告诉mybatis-plus， sql映射文件
 *  2. 逻辑删除
 *  1. 配置全局的逻辑删除规则 （省略）
 *  2. 配置逻辑删除的组件bean (3.1 以上省略）
 *  3. 加上逻辑删除注解
 *
 *  3. JSR303 java规则
 *    1. 给bean添加校验规则 javax.validation.constraints,并自定义message提示
 *    2. 开启校验功能 @Valid
 *       效果：校验错误以后会有默认的响应
 *    3. 给校验的bean后紧跟一个BindingResult，就可以得到校验的结果
 *    4. 分组校验（多场景的复杂校验）
 *    	 1. @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, UpdateGroup.class})
 *        给校验注解标注什么情况需要进行校验
 *      2. controller 里面方法加 @Validated({AddGroup.class})
 *      3. 默认没有指定分组的校验注解，在分组情况下则不生效，只会在@Validated情况下生效
 *
 *   5. 自定义校验
 *      1. 编写一个自定义的校验注解
 *      2. 编写一个自定义的校验器
 *      3. 关联校验注解和校验器
 *
 *
 *
 *
 *    4.统一的异常处理
 * @ContorllerAdvice
 *
 *
 *
 *
 *
 *
 *
 */
@EnableCaching
@EnableFeignClients(basePackages = "com.ym.gulimall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.ym.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
