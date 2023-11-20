package com.leikooo.yubi.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public class TTLProduct {
    private final static String QUEUE_NAME = "TTL";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            // 发送端指定每条消息的过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("60000")
                    .build();
            channel.basicPublish("", QUEUE_NAME, properties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
