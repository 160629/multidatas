package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.c")
public class TopicCustomerC {
	
	@RabbitHandler
	public void showMessage(String message) {
		System.out.println("TopicCustomerC>>>topic.c>>>接收到消息："+message);
	}
}
