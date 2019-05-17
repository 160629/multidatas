package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Result;
import com.example.demo.db01.entity.User01;
import com.example.demo.db01.service.User01Service;
import com.example.demo.db01.view.User01V;
import com.example.demo.db02.entity.User02;
import com.example.demo.db02.service.User02Service;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.BeanUtil;
import com.example.starter.service.DemoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Api(tags = "用户管理")
@RestController
public class IndexController {
	@Autowired
	User01Service user01Service;
	@Autowired
	User02Service user02Service;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	DemoService demoService;
	
	@GetMapping("/say")
	public String sayWhat(){
		return demoService.say();
	}

	@ApiOperation(value = "用户添加 通用方法测试")
	@ApiImplicitParam(paramType = "query", name = "aa", value = "aa=0测试回滚", dataType = "Integer")
	@GetMapping("testAdd0102")
	public 	Integer testAdd0102(Integer aa) throws Exception {
		User01 user01 = new User01();
		user01.setUserName("44" + aa);
		user01.setUserAge(aa);
		User02 user02 = new User02();
		user02.setUserName("55" + aa);
		user02.setUserAge(aa);
		//int addUser0102 = userService.addUser12(user01, user02);
		int addUser01 = userService.addUser01(user01);
		int addUser02 = userService.addUser021(user02);
		return addUser02;
	}
	
	
	@ApiOperation(value = "用户全部查询")
	@GetMapping("selectUsers")
	public Result<List<User01V>> selectUsers() {

		try {
			List<User01> selectUsers = user01Service.selectUsers();
			List<User01V> list = BeanUtil.objectExchanges(selectUsers, User01V.class);
			return new Result<List<User01V>>(200, "查询成功",new Date(), list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<List<User01V>>(500, "程序处理失败",new Date(), null);
		}
	}

	@ApiOperation(value = "用户添加")
	@ApiImplicitParam(paramType = "query", name = "aa", value = "aa=0测试回滚", dataType = "Integer")
	@GetMapping("testAdd")
	@Transactional
	public Result<Integer> testAdd(Integer aa) throws Exception {
		User01 user01 = new User01();
		user01.setUserName("22" + aa);
		user01.setUserAge(aa);
		User02 user02 = new User02();
		user02.setUserName("33" + aa);
		user02.setUserAge(aa);
		try {
			int h1 = user01Service.addUser01(user01);
			System.out.println(h1);
			h1 = 1 / aa;
			int h2 = user02Service.addUser02(user02);
			System.out.println(h2);
			return new Result<Integer>(200, "添加成功",new Date(), h1+h2);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception();
			return new Result<Integer>(500, "程序处理失败",new Date(), null);
		}

	}
	
	@ApiOperation(value = "用户分页")
	@GetMapping("userPages")
	public Result<PageInfo> userPages() {
		int pageNum = 1;
		int pageSize = 2;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<User01> selectUsers = user01Service.selectUsers();
			PageInfo page = new PageInfo(selectUsers);
			return new Result<PageInfo>(200, "添加成功",new Date(), page);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<PageInfo>(500, "程序处理失败",new Date(), null);
		}

	}
}
