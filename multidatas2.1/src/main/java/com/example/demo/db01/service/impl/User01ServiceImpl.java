package com.example.demo.db01.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db01.entity.User01;
import com.example.demo.db01.mapper.User01Mapper;
import com.example.demo.db01.service.User01Service;
import com.example.demo.db02.entity.User02;
import com.example.demo.db02.mapper.User02Mapper;

@Service
public class User01ServiceImpl implements User01Service {
	
	
	@Autowired
	User01Mapper user01Mapper;
	@Autowired
	User02Mapper user02Mapper;
	@Override
	public List<User01> selectUsers() {
		// TODO Auto-generated method stub
		List<User01> selectAll = user01Mapper.selectAll();
		System.out.println(selectAll);
		List<User02> selectAll2 = user02Mapper.selectAll();
		System.out.println(selectAll2);
		return user01Mapper.selectUsers();
	}
	@Override
	public int addUser01(User01 user01) {
		// TODO Auto-generated method stub
		return user01Mapper.addUser01(user01);
	}
	@Override
	public int addUser011(User01 user01) {
		// TODO Auto-generated method stub
		return user01Mapper.insertSelective(user01);
	}

}
