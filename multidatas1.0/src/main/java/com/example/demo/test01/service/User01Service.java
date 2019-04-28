
package com.example.demo.test01.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.test01.entity.User;
import java.util.List;


public interface User01Service extends IService<User>{


	int addUser(String name, Integer age);
	
	List<User> list();
	
	User selectById(Integer id);

}
