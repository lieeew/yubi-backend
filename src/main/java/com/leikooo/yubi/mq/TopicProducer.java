package com.leikooo.yubi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
public class TopicProducer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String userInput = sc.nextLine();
                String[] strings = userInput.split(" ");
                if (strings.length < 1) {
                    continue;
                }
                String routingKey = strings[0];
                String message = strings[1];
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent  routingKey'" + routingKey + "' message:'" + message + "'");
            }
        }
    }
}