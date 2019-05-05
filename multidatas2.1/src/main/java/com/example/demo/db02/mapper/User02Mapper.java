package com.example.demo.db02.mapper;

import java.util.List;

import com.example.demo.db02.entity.User02;

import tk.mybatis.mapper.common.Mapper;

public interface User02Mapper extends Mapper<User02>{
	List<User02> selectUsers();
	
	int addUser02(User02 user02);
}
