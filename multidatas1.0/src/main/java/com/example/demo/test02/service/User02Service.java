
package com.example.demo.test02.service;

import com.example.demo.test02.mapper.User2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface User02Service {

	
	int addUser(String name,Integer age);

}
