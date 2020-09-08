package com.ym.gulimall.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct // MyRabbitConfig 对象创建完成之后，执行这个方法
    public void initRabbitTemplate() {

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 1. 做好消息确认机制(publisher, consumer[手动ack])
             * 2. 每一个发送的消息都在数据库做好记录，定期将失败的消息再次发送一遍
             * @param correlationData 当前消息的唯一关联数据（这个是消息的唯一id）
             * @param b 消失是否成功收到
             * @param s 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                // 服务器收到
                // 修改消息的状态
                System.out.println("confirm...correlationData【" + correlationData + "because " + b + "s " + s);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message 指定石板的消息 详细信息
             * @param i   回复的状态码
             * @param s   回复的文本内容
             * @param s1   当地这个消息给哪个交换机
             * @param s2   当时这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {

                // 报错了，修改数据库当前消息的状态-》错误
                System.out.println("Failed message " + message + i + s + s1 + s2);
            }
        });
    }
}
