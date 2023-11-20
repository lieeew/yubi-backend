package com.leikooo.yubi.mq;

import com.rabbitmq.client.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
public class DirectConsumer {

    private static final String EXCHANGE_NAME = "direct-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明一个 direct 交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String queueName1 = "yupi_queue";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, EXCHANGE_NAME, "yupi");

        String queueName2 = "leikooo_queue";
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, EXCHANGE_NAME, "leikooo");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [yupi] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [leikooo] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queueName1, true, deliverCallback1, consumerTag -> {
        });
        channel.basicConsume(queueName2, true, deliverCallback2, consumerTag -> {
        });

    }
}