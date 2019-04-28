package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//@ComponentScan(basePackages = { "com.example.demo.config","com.example.demo.controller","com.example.demo.datasource","com.example.demo.test01","com.example.demo.test02"})
@EnableAutoConfiguration
@SpringBootApplication
public class MultidatasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultidatasApplication.class, args);
	}

}
