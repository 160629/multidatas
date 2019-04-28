
package com.example.demo.test02.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.test02.entity.User;
@CacheConfig(cacheNames = "baseCache")
public interface User2Mapper extends BaseMapper<User>{

	@Insert("insert into user (userName,userAge) values(#{userName},#{userAge});")
	Integer addUser(@Param("userName") String userName, @Param("userAge") Integer userAge);

}
