package com.leikooo.yubi.mq;

import com.rabbitmq.client.*;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
public class FanoutConsumer {
    private static final String EXCHANGE_NAME = "fanout-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel1 = connection.createChannel();
        Channel channel2 = connection.createChannel();
        // 声明交换机
        channel1.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // String name1 = "小王_queue";
        // channel1.queueDeclare(name1, false, false, false, null);
        // 直接创建一个默认名称队列, 用完就走的队列
        String queueName = channel1.queueDeclare().getQueue();
        channel1.queueBind(queueName, EXCHANGE_NAME, "");

        // String name2 = "小李_queue";
        // channel2.queueDeclare(name2, false, false, false, null);
        String queueName2 = channel2.queueDeclare().getQueue();
        channel2.queueBind(queueName2, EXCHANGE_NAME, "");


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] queue1 Received '" + message + "'");
        };
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] queue2 Received '" + message + "'");
        };

        channel1.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
        channel2.basicConsume(queueName2, true, deliverCallback2, consumerTag -> {
        });
    }
}