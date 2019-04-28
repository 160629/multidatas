
package com.example.demo.test01.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.test01.entity.User;


@CacheConfig(cacheNames = "baseCache")
public interface User1Mapper extends BaseMapper<User>{
	@Insert("insert into user (userName,userAge) values(#{userName},#{userAge});")
	int addUser(@Param("userName") String userName, @Param("userAge") Integer userAge);

}
