package com.example.demo.db01.service;

import java.util.List;

import com.example.demo.db01.entity.User01;

public interface User01Service {
	List<User01> selectUsers();
	int addUser01(User01 user01);
}
