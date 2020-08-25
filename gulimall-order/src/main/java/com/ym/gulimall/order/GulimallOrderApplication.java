package com.ym.gulimall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 使用rabbitmq
 * 1. 引入amqp， RabbitAutoConfiguration自动生效
 * 2. 给容器中自动配置了
 *     RabbitTemplate, AmqpAdmin, CachingConnectionFactory, RabbitMessagingTemplate
 * 3. 给配置文件中配置spring.rabbitmq信息
 * 4. @EnableRabbit
 */

@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallOrderApplication.class, args);
    }

}
