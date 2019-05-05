package com.example.demo.db02.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db02.entity.User02;
import com.example.demo.db02.mapper.User02Mapper;
import com.example.demo.db02.service.User02Service;
@Service
public class User02ServiceImpl implements User02Service{

	@Autowired
	User02Mapper user02Mapper;
	@Override
	public List<User02> selectUsers() {
		// TODO Auto-generated method stub
		return user02Mapper.selectUsers();
	}
	@Override
	public int addUser02(User02 user02) {
		// TODO Auto-generated method stub
		return user02Mapper.addUser02(user02);
	}
	@Override
	public int addUser021(User02 user02) {
		// TODO Auto-generated method stub
		return user02Mapper.insertSelective(user02);
	}

}
