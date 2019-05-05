package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.demo.config.DBConfig01;
import com.example.demo.config.DBConfig02;

@SpringBootApplication(scanBasePackages={"com.example.demo.*"})
@EnableConfigurationProperties(value = { DBConfig01.class, DBConfig02.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
