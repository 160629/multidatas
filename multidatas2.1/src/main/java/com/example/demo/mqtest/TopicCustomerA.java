package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.a")
public class TopicCustomerA {
	
	@RabbitHandler
	public void showMessage(String message) {
		System.out.println("TopicCustomerA>>>topic.a>>>接收到消息："+message);
	}
}
