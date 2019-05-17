package com.example.demo.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db01.entity.User01;
import com.example.demo.db01.mapper.User01Mapper;
import com.example.demo.db02.entity.User02;
import com.example.demo.db02.mapper.User02Mapper;
import com.example.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	User02Mapper user02Mapper;
	
	@Autowired
	User01Mapper user01Mapper;

	@Transactional
	@Override
	public int addUser0102(User01 user01, User02 user02,Integer aa) {
		int i = user01Mapper.insertSelective(user01);
		i=1/aa;
		int k = user02Mapper.insertSelective(user02);
		return i+k;
	}
	@Transactional
	//@Override
	public int addUser12(User01 user01, User02 user02) {
		int i = addUser01(user01);
		
		int k = addUser021(user02);
		
		return i+k;
	}
	@Transactional
	@Override
	public int addUser01(User01 user01) {
		// TODO Auto-generated method stub
		return user01Mapper.insertSelective(user01);
	}
	@Transactional
	@Override
	public int addUser021(User02 user02) {
		// TODO Auto-generated method stub
		addUser02(user02);
		 int i=1/0;
		return i;
	}
	
	@Override
	public int addUser02(User02 user02) {
		// TODO Auto-generated method stub
		 int i = user02Mapper.insertSelective(user02);
		return i;
	}

}
