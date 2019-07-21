package com.fate.spring.multiple.mybatis.mapper.testdb;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.fate.spring.multiple.mybatis.entity.User;

public interface UserMapper {
	
	@Select("select * from user ")
	List<User> getAll();

}
