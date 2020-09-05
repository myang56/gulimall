package com.ym.gulimall.order.controller;

import com.ym.gulimall.order.entity.OrderEntity;
import com.ym.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMq")
    public String sendMq(@RequestParam(value = "num", defaultValue = "10") Integer num) {

        for (int i = 0; i < num; i++) {

            if (i % 2 == 0) {
                OrderReturnReasonEntity returnReasonEntity = new OrderReturnReasonEntity();
                returnReasonEntity.setId(1L);
                returnReasonEntity.setCreateTime(new Date());
                returnReasonEntity.setName("haha " + i);
                String msg = "Hello World";
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java",  returnReasonEntity);
                log.info("消息发送完成{}",  returnReasonEntity);
            } else {

                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(UUID.randomUUID().toString());
                String msg = "Hello World";
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java",  orderEntity);
                log.info("消息发送完成{}",  orderEntity);
            }
        }
        return "ok";
    }
}
