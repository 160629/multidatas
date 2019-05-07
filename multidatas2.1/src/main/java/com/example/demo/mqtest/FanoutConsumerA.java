package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="fanout.a")
public class FanoutConsumerA{
	@RabbitHandler
	public void showMessage(String message){
		System.out.println(">>>FanoutConsumerA >>>fanout.a>>>接收到消息" +message);
	}
}