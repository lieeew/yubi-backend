package com.leikooo.yubi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
public class TopicConsumer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        String backendQueue = "backend_queue";
        channel.queueDeclare(backendQueue, true, false, false, null);
        // 绑定路由 routingKey 规则
        channel.queueBind(backendQueue, EXCHANGE_NAME, "#.后端.#");

        String frontedQueue = "frontend_queue";
        channel.queueDeclare(frontedQueue, true, false, false, null);
        channel.queueBind(frontedQueue, EXCHANGE_NAME, "#.前端.#");

        String productQueue = "product_queue";
        channel.queueDeclare(productQueue, true, false, false, null);
        channel.queueBind(productQueue, EXCHANGE_NAME, "#.产品.#");

        DeliverCallback frontendDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [fronted] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        DeliverCallback backendDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [backend] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

       DeliverCallback productDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [product] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(backendQueue, true, backendDeliverCallback, consumerTag -> {
        });

        channel.basicConsume(frontedQueue, true, frontendDeliverCallback, consumerTag -> {
        });

        channel.basicConsume(productQueue, true, productDeliverCallback, consumerTag -> {});

    }
}