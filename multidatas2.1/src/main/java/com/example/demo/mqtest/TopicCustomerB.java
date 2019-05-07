package com.example.demo.mqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.b")
public class TopicCustomerB {
	
	@RabbitHandler
	public void showMessage(String message) {
		System.out.println("TopicCustomerB>>>topic.b>>>接收到消息："+message);
	}
}
