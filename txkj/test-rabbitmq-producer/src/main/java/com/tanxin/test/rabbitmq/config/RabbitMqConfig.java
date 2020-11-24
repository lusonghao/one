package com.tanxin.test.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final  String QUEUE_INFORM_EMAIL = "QUEUE_INFORM_EMAIL";
    public static final  String QUEUE_INFORM_SMS = "QUEUE_INFORM_SMS";
    public static final  String EXCHANGE_INFORM_NAME = "EXCHANGE_INFORM_NAME";
    public static final  String INFORM_KEY_EMAIL = "inform.#.email.#";
    public static final  String INFORM_KEY_SMS = "inform.#.sms.#";

    @Bean(EXCHANGE_INFORM_NAME)
    public Exchange EXCHANGE_INFORM_NAME(){
        return ExchangeBuilder.topicExchange(EXCHANGE_INFORM_NAME).durable(true).build();
    }

    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        return new Queue(QUEUE_INFORM_SMS);
    }

    @Bean
    public Binding BINGDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                               @Qualifier(EXCHANGE_INFORM_NAME) Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(INFORM_KEY_EMAIL).noargs();

    }


    @Bean
    public Binding BINGDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                               @Qualifier(EXCHANGE_INFORM_NAME) Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(INFORM_KEY_SMS).noargs();

    }






}
