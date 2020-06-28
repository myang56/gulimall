package com.ym.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
 */
@EnableDiscoveryClient
@MapperScan("com.ym.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
