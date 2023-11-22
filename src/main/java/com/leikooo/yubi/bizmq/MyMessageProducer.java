package com.leikooo.yubi.bizmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;


public class MyMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}