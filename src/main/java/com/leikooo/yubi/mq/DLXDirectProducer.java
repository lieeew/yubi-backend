package com.leikooo.yubi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * 死信
 */
public class DLXDirectProducer {

    private static final String EXCHANGE_NAME = "direct-exchange";
    private static final String DLX_EXCHANGE_NAME = "dlx_direct-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明消息处理的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 声明死信交换机
            channel.exchangeDeclare(DLX_EXCHANGE_NAME, "direct");
            // 声明死信队列
            String queueName3 = "boss";
            channel.queueDeclare(queueName3, true, false, false, null);
            channel.queueBind(queueName3, DLX_EXCHANGE_NAME, "boss", null);

            String queueName4 = "outsourcing";
            channel.queueDeclare(queueName4, true, false, false, null);
            channel.queueBind(queueName4, DLX_EXCHANGE_NAME, "outsourcing", null);

            // 正常的消息队列
            String queueName = "小王";
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
            args.put("x-dead-letter-routing-key", "outsourcing");
            channel.queueDeclare(queueName, true, false, false, args);
            channel.queueBind(queueName, EXCHANGE_NAME, "小王", null);

            String queueName2 = "小李";
            Map<String, Object> args2 = new HashMap<>();
            args2.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
            args2.put("x-dead-letter-routing-key", "boss");
            channel.queueDeclare(queueName2, true, false, false, args2);
            channel.queueBind(queueName2, EXCHANGE_NAME, "小李", null);

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