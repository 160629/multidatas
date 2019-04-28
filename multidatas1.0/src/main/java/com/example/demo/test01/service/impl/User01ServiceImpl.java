
package com.example.demo.test01.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.test01.entity.User;
import com.example.demo.test01.mapper.User1Mapper;
import com.example.demo.test01.service.User01Service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class User01ServiceImpl extends ServiceImpl<User1Mapper, User> implements User01Service{

	public int addUser(String name, Integer age) {
		return baseMapper.addUser(name, age);
	}

	@Override
	public List<User> list() {
		EntityWrapper<User> wrapper = new EntityWrapper<>();
		System.out.println(11111);
		return baseMapper.selectList(wrapper);
	}
	
	@Override
	public User selectById(Integer id) {
		System.out.println(22222);
		return baseMapper.selectById(id);
	}

}
