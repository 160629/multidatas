package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db01.entity.User01;
import com.example.demo.db01.service.User01Service;
import com.example.demo.db02.entity.User02;
import com.example.demo.db02.service.User02Service;

import java.util.List;

import javax.transaction.Transactional;

@RestController
public class Indexcontroller {
    @Autowired
    User01Service  user01Service;
    @Autowired
    User02Service  user02Service;
    
    @GetMapping("selectUsers")
    public List<User01> selectUsers() {
    	return user01Service.selectUsers();
    }
    
/*    
    @GetMapping("selectById")
    public User01 selectById(Integer id) {
    	return user01Service.selectById(id);
    }
*/    
    @GetMapping("testAdd")
    @Transactional
    public String testAdd(Integer aa) {
    	User01 user01 = new User01();
    	user01.setUserName("22"+aa);
    	user01.setUserAge(aa);
    	User02 user02 = new User02();
    	user02.setUserName("33"+aa);
    	user02.setUserAge(aa);
    	int h = user01Service.addUser011(user01);
    	System.out.println(h);
    	h=1/aa;
    	int addUser02 = user02Service.addUser021(user02);
    	System.out.println(addUser02);
    	return "成功";
    }
}
