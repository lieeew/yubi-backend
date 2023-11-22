package com.leikooo.yubi.bizmq;

import com.leikooo.yubi.constant.BIMQConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
public class BIMqInitMain {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // 声明死信队列
            channel.exchangeDeclare(BIMQConstant.BI_DLX_EXCHANGE_NAME, "direct");
            channel.queueDeclare(BIMQConstant.BI_DLX_QUEUE_NAME, true, false, false, null);
            channel.queueBind(BIMQConstant.BI_DLX_QUEUE_NAME, BIMQConstant.BI_DLX_EXCHANGE_NAME, BIMQConstant.BI_DLX_ROUTING_KEY);

            channel.exchangeDeclare(BIMQConstant.BI_EXCHANGE_NAME, "direct");

            Map<String, Object> arg = new HashMap<String, Object>();
            arg.put("x-dead-letter-exchange", BIMQConstant.BI_DLX_EXCHANGE_NAME);
            arg.put("x-dead-letter-routing-key", BIMQConstant.BI_DLX_ROUTING_KEY);
            channel.queueDeclare(BIMQConstant.BI_QUEUE_NAME, true, false, false, arg);
            channel.queueBind(BIMQConstant.BI_QUEUE_NAME, BIMQConstant.BI_EXCHANGE_NAME, BIMQConstant.BI_ROUTING_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
