package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="fanout.c")
public class FanoutConsumerC{
	@RabbitHandler
	public void showMessage(String message){
		System.out.println(">>>FanoutConsumerC >>>fanout.c>>>接收到消息" +message);
	}
}