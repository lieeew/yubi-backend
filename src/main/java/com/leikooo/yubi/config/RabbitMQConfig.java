package com.leikooo.yubi.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.leikooo.yubi.constant.BIMQConstant.*;

/**
 * @author leikooo
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 声明死信交换机
     */
    @Bean
    public DirectExchange biDlxExchange() {
        return new DirectExchange(BI_DLX_EXCHANGE_NAME);
    }

    /**
     * 声明死信队列
     */
    @Bean
    public Queue biDlxQueue() {
        return new Queue(BI_DLX_QUEUE_NAME, true, false, false);
    }

    /**
     * 将死信队列绑定到死信交换机
     */
    @Bean
    public Binding biDlxBinding(Queue biDlxQueue, DirectExchange biDlxExchange) {
        return BindingBuilder.bind(biDlxQueue).to(biDlxExchange).with(BI_DLX_ROUTING_KEY);
    }

    /**
     * 声明主交换机
     */
    @Bean
    public DirectExchange biExchange() {
        return new DirectExchange(BI_EXCHANGE_NAME);
    }

    /**
     * 声明主队列并指定其死信交换机配置
     */
    @Bean
    public Queue biQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", BI_DLX_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", BI_DLX_ROUTING_KEY);
        return new Queue(BI_QUEUE_NAME, true, false, false, args);
    }

    /**
     * 将主队列绑定到主交换机
     */
    @Bean
    public Binding biBinding(Queue biQueue, DirectExchange biExchange) {
        return BindingBuilder.bind(biQueue).to(biExchange).with(BI_ROUTING_KEY);
    }
}
