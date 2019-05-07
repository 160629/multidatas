package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class DirectCcustomer01 {
    @RabbitHandler
    public void showMessage(String message){
        System.out.println("DirectCcustomer01 >>>hello>>>接收到消息："+message);
    }
}
