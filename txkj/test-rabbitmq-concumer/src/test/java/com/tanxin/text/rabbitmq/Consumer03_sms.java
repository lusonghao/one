package com.tanxin.text.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer03_sms {

    private static final  String QUEUE_ROUTING_SMS = "QUEUE_ROUTING_SMS";
    private static final  String EXCHANGE_ROUTING_NAME = "EXCHANGE_ROUTING_NAME";
    private static final  String ROUTING_KEY_SMS = "EXCHANGE_TEST_SMS";

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
        channel.queueDeclare(QUEUE_ROUTING_SMS,true,false,false,null);
        channel.exchangeDeclare(EXCHANGE_ROUTING_NAME,BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_ROUTING_SMS,EXCHANGE_ROUTING_NAME,ROUTING_KEY_SMS);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String exchange = envelope.getExchange();
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body,"utf-8");
                System.out.println(message+"邮件routing");

            }
        };
        channel.basicConsume(QUEUE_ROUTING_SMS,true,defaultConsumer);
    }

}
