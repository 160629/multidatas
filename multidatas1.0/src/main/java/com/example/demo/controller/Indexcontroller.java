package com.example.demo.controller;

import com.example.demo.test01.entity.User;
import com.example.demo.test01.mapper.User1Mapper;
import com.example.demo.test01.service.User01Service;
import com.example.demo.test02.mapper.User2Mapper;
import com.example.demo.test02.service.User02Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.transaction.Transactional;

@RestController
public class Indexcontroller {
    @Autowired
    private User2Mapper user2Mapper;
    
    @Autowired
    private User01Service user01Service;
    @Autowired
   private User02Service user02Service;


    @GetMapping("/add")
    @Transactional
    public String add(Integer aa) {
    	user02Service.addUser("Clare"+aa, aa);
        //int i = 1/aa;
       user01Service.addUser("Tung"+aa, aa);
        return "成功！";
    }
    
    @GetMapping("/list")
    public List<User> list() {
    	return user01Service.list();
    }
    
    @GetMapping("/selectById")
    public User selectById(Integer id) {
    	return user01Service.selectById(id);
    }
}
