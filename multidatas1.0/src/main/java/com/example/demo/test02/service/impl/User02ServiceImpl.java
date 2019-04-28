
package com.example.demo.test02.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.test02.entity.User;
import com.example.demo.test02.mapper.User2Mapper;
import com.example.demo.test02.service.User02Service;

import org.springframework.stereotype.Service;


@Service
public class User02ServiceImpl extends ServiceImpl<User2Mapper, User> implements User02Service{
	
	public int addUser(String name,Integer age){
		return baseMapper.addUser(name, age);
	}

}
