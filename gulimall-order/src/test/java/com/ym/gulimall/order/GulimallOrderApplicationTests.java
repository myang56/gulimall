package com.ym.gulimall.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello-java", null);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    void contextLoads() {

    }

}
