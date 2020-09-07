package com.ym.gulimall.order.config;



import com.rabbitmq.client.Channel;
import com.ym.gulimall.order.entity.OrderEntity;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyMQconfig {

//    @Bean Binding, Queue, Exchange



    /**
     * 容器中Binding, Queue, Exchange都会自动创建（如果rabbitMQ没有的话）
     * 如果RabbitMQ已经创建后，如果改queue里面的参数也不会更新，需要先在rabbitmq里面删除，重启才能更新
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        arguments.put("x-message-ttl", 60000);

        Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
        return queue;

    }

    @Bean
    public Queue orderReleaseOrderQueue() {

        Queue queue = new Queue("order.release.order.queue", true, false, false);
        return queue;

    }

    @Bean
    public Exchange orderEventExchange() {

        return new TopicExchange("order-event-exchange", true, false);
    }

    @Bean
    public Binding orderCreateOrderBinding() {

        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order", null);
    }

    @Bean
    public Binding orderReleaseOrderBinding() {

        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order", null);

    }

    /**
     * 订单释放直接和库存释放绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.order.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#", null);
    }
}
