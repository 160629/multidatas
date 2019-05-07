package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.RedisHelper;

import io.swagger.annotations.Api;

@Api(tags = "redis测试")
@RestController
public class RedisController {
	@Autowired
	private RedisHelper redisHelper;
	
	@GetMapping("setValue")
	public String setValue(Integer num) {
		redisHelper.getValueOperations().set("key"+num, num.toString());
		return "添加成功";
	}
	
	@GetMapping("getValue")
	public String getValue(Integer num) {
		return redisHelper.getValueOperations().get("key"+num).toString();
	}
}
