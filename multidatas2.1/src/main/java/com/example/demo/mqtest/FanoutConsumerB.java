package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="fanout.b")
public class FanoutConsumerB{
	@RabbitHandler
	public void showMessage(String message){
		System.out.println(">>>FanoutConsumerB >>>fanout.b>>>接收到消息" +message);
	}
}