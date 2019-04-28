package com.example.demo.db01.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db01.entity.User01;
import com.example.demo.db01.mapper.User01Mapper;
import com.example.demo.db01.service.User01Service;

@Service
public class User01ServiceImpl implements User01Service {
	
	
	@Autowired
	User01Mapper user01Mapper;
	@Override
	public List<User01> selectUsers() {
		// TODO Auto-generated method stub
		return user01Mapper.selectUsers();
	}
	@Override
	public int addUser01(User01 user01) {
		// TODO Auto-generated method stub
		return user01Mapper.addUser01(user01);
	}

}
