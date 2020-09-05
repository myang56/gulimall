package com.ym.gulimall.order;

import com.ym.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j
@SpringBootTest
class GulimallOrderApplicationTests {

    /**
     * 1. how to create exchanges,  queue, binding
     *    use AmqpAdmin
     * how to send/read message
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;



    @Test
    void sendMessageTest() {

        for (int i = 0; i < 10; i++) {
            OrderReturnReasonEntity returnReasonEntity = new OrderReturnReasonEntity();
            returnReasonEntity.setId(1L);
            returnReasonEntity.setCreateTime(new Date());
            returnReasonEntity.setName("haha " + i);

            String msg = "Hello World";
            rabbitTemplate.convertAndSend("hello-java-exchange", "hello-java",  returnReasonEntity);
            log.info("消息发送完成{}",  returnReasonEntity);
        }

    }

    @Test
    void createExchange() {

        // DirectExchange(String name, boolean durable, boolean autoDelete)
        DirectExchange directExchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功", directExchange.getName());
    }

    @Test
    void createQueue() {

        // Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Queue queue = new Queue("hello-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("Exchange[{}]创建成功", queue.getName());
    }

    @Test
    void createBinding() {
        // Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments)
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    void contextLoads() {

    }

}
