package com.example.demo.db02.mapper;

import java.util.List;

import com.example.demo.db02.entity.User02;

public interface User02Mapper {
	List<User02> selectUsers();
	
	int addUser02(User02 user02);
}
