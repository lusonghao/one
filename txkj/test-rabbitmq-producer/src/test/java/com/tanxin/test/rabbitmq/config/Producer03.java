package com.tanxin.test.rabbitmq.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer03 {

    private static final  String QUEUE_ROUTING_EMAIL = "QUEUE_ROUTING_EMAIL";
    private static final  String QUEUE_ROUTING_SMS = "QUEUE_ROUTING_SMS";
    private static final  String EXCHANGE_ROUTING_NAME = "EXCHANGE_ROUTING_NAME";
    private static final  String ROUTING_KEY_EMAIL = "EXCHANGE_TEST_EMAIL";
    private static final  String ROUTING_KEY_SMS = "EXCHANGE_TEST_SMS";


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        connectionFactory.setVirtualHost("/");

        Connection connection=null;
        Channel channel =null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_ROUTING_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_ROUTING_SMS,true,false,false,null);
            channel.exchangeDeclare(EXCHANGE_ROUTING_NAME, BuiltinExchangeType.DIRECT);
            channel.queueBind(QUEUE_ROUTING_EMAIL,EXCHANGE_ROUTING_NAME,ROUTING_KEY_EMAIL);
            channel.queueBind(QUEUE_ROUTING_SMS,EXCHANGE_ROUTING_NAME,ROUTING_KEY_SMS);




            for (int i=0;i<5;i++){

                String message = "send inform message to user email";
                channel.basicPublish(EXCHANGE_ROUTING_NAME,ROUTING_KEY_EMAIL,null,message.getBytes());
                System.out.println("send to mq"+message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
