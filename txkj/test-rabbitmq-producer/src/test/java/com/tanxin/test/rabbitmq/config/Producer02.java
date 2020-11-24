package com.tanxin.test.rabbitmq.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer02 {

    private static final  String QUEUE_TEST_EMAIL = "QUEUE_TEST_EMAIL";
    private static final  String QUEUE_TEST_SMS = "QUEUE_TEST_SMS";
    private static final  String EXCHANGE_TEST_NAME = "EXCHANGE_TEST_NAME ";

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
            channel.queueDeclare(QUEUE_TEST_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_TEST_SMS,true,false,false,null);
            channel.exchangeDeclare(EXCHANGE_TEST_NAME, BuiltinExchangeType.FANOUT);
            channel.queueBind(QUEUE_TEST_EMAIL,EXCHANGE_TEST_NAME,"");
            channel.queueBind(QUEUE_TEST_SMS,EXCHANGE_TEST_NAME,"");




            for (int i=0;i<5;i++){

                String message = "send inform message to user";
                channel.basicPublish(EXCHANGE_TEST_NAME,"",null,message.getBytes());
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
