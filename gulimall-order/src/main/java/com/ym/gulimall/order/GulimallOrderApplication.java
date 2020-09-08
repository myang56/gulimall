package com.ym.gulimall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 使用rabbitmq
 * 1. 引入amqp， RabbitAutoConfiguration自动生效
 * 2. 给容器中自动配置了
 * RabbitTemplate, AmqpAdmin, CachingConnectionFactory, RabbitMessagingTemplate
 * 3. 给配置文件中配置spring.rabbitmq信息
 * 4. @EnableRabbit
 * 5  监听消息，@RabbitListener  class + method
 * 6. @RabbitHandler  method only
 * <p>
 * 本地事务失效问题
 * 同一个对象内事务方法互调默认失效，原因 绕过了代理对象，事务使用代理对象来控制的
 * 解决：使用代理对象来调用事务方法
 * 1. 引入spring-boot-starter-aop，引入aspectj
 * 2. @EnableAspectJAutoProxy 开启aspctj动态代理功能，以后所有代理都有ascpectj创建，没有接口也可以
 * 3. 用代理对象互调
 * <p>
 * Seata 控制分布式事务
 * 1. 每一个微服务先必须创建undo-log表格
 * 2. 安装事先协调器，seata-server 解压并启动seata-server  修改register type为nacos
 * 3. 代理数据源
 */

@EnableAspectJAutoProxy
@EnableFeignClients
@EnableRedisHttpSession
@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallOrderApplication.class, args);
    }

}
