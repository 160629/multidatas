package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
	
	//分列式 交换器
	@Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
	
	//定义队列 A start
	@Bean
    public Queue fanoutA() {
        return new Queue("fanout.a");
    }
	
	//队列绑定交换机 A start
	@Bean
    public Binding bindingExchangeWithA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }
	
	//定义队列 B start
	@Bean
	public Queue fanoutB() {
		return new Queue("fanout.b");
	}
	
	//队列绑定交换机 B start
	@Bean
	public Binding bindingExchangeWithB() {
		return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
	}
	//定义队列 C start
	@Bean
	public Queue fanoutC() {
		return new Queue("fanout.c");
	}

	
	//队列绑定交换机 C start
	@Bean
	public Binding bindingExchangeWithC() {
		return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
	}


}
