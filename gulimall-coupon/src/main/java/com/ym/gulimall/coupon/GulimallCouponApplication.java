package com.ym.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * 1. 如何使用Nacos作为配置中心统一管理配置
 * 1. 引入依赖
 * 2. 创建bootstrap.properties
 * spring.application.name=gulimall-coupon
 * spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 * 3. 需要给配置中心默认添加一个叫 数据集(Data Id)gulimall-coupon.properties 默认规则（应用名.properties)
 * 4. 应用名.properties 添加任何配置
 * 5. 动态获取配置
 *
 * @RefreshScope 动态获取并刷新配置
 * @Value("${coupon.user.name}") 如果配置中心和当前应用配置文件中配置了相同的项，优先使用Naco配置中心的值
 * 2. 细节
 * 1）命名空间： 配置隔离
 * 默认：public（保留空间）：默认新增的所有配置都在public空间
 * 1. 开发，测试， 生成： 利用命名空间来做环境隔离
 * 注意：在bootstrap.properties, 配置上需要使用哪个命名空间下的命名空间ID
 * 2. 每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己服务命名空间的配置
 * <p>
 * 2) 配置集： 所有配置集合
 * 3）配置集ID: 配置文件名（Data ID)  配置文件名
 * 4） 配置分组： 默认所有的配置集都属于: DEFAULT_GROUP
 * <p>
 * 每一个微服务创建自己的命名空间，使用配置分组区分环境，dev, test, prod
 * <p>
 * 3. 同时加载多个配置集
 * 1）微服务任何配置信息，任何配置文件都可以放在配置中心中
 * 2） 只需要在bootstrap.properties 说明加载配置中心中哪些配置文件即可
 * 3） @Value, @ConfigurationProperties.
 * 以前springboot任何方法从配置文件中获取值，都能使用
 * 配置中心有的优选使用配置中心中的
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
