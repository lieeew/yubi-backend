package com.leikooo.yubi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
public class DLXDirectConsumer {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [yupi] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
        };

        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [leikooo] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
        };

        DeliverCallback deliverCallback3 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [boss] 死信队列 Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        DeliverCallback deliverCallback4 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [outsourcing] 死信队列 Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume("小王", false, deliverCallback1, consumerTag -> {
        });
        channel.basicConsume("小李", false, deliverCallback2, consumerTag -> {
        });
        channel.basicConsume("boss", false, deliverCallback3, consumerTag -> {});

        channel.basicConsume("outsourcing", false, deliverCallback3, consumerTag -> {});
    }
}