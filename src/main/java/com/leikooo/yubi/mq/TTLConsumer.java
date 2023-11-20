package com.leikooo.yubi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public class TTLConsumer {
    private final static String QUEUE_NAME = "TTL";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("x-message-ttl", 60000);
            channel.queueDeclare(QUEUE_NAME, true, false, false, map);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
        }
    }
}
