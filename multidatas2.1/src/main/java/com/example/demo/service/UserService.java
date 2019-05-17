package com.example.demo.service;

import com.example.demo.db01.entity.User01;
import com.example.demo.db02.entity.User02;

public interface UserService {

	int addUser0102(User01 user01,User02 user02,Integer aa);
	
//	int addUser12(User01 user01,User02 user02);
	
	int addUser01(User01 user01);
	
	int addUser02(User02 user02);
	int addUser021(User02 user02);
}
