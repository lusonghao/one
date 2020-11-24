package com.tanxin.text.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02_email {

    private static final  String QUEUE_TEST_EMAIL = "QUEUE_TEST_EMAIL";
    private static final  String EXCHANGE_TEST_NAME = "EXCHANGE_TEST_NAME ";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        connectionFactory.setVirtualHost("/");

        Connection connection=null;

        connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_TEST_EMAIL,true,false,false,null);
        channel.exchangeDeclare(EXCHANGE_TEST_NAME,BuiltinExchangeType.FANOUT);
        channel.queueBind(QUEUE_TEST_EMAIL,EXCHANGE_TEST_NAME,"");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String exchange = envelope.getExchange();
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body,"utf-8");
                System.out.println(message+"邮件消费者啥也不是");

            }
        };
        channel.basicConsume(QUEUE_TEST_EMAIL,true,defaultConsumer);
    }

}
