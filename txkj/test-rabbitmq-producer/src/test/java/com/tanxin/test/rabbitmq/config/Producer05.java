package com.tanxin.test.rabbitmq.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05 {

    @Resource
    RabbitTemplate rabbitTemplate;


    @Test
    public void testSendEmail(){
        String message = "send email message to user";
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_INFORM_NAME,"inform.email",message);

    }
}
