package com.fate.spring.multiple.mybatis.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fate.spring.multiple.mybatis.entity.City;
import com.fate.spring.multiple.mybatis.entity.User;
import com.fate.spring.multiple.mybatis.mapper.testdb.UserMapper;
import com.fate.spring.multiple.mybatis.mapper.world.CityMapper;

@Controller
public class MultDataSourceController {
	
	@Autowired
	private CityMapper cityMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value="/user")
	@ResponseBody
	public List<User> getUser(){
		return userMapper.getAll();
	}
	
	@RequestMapping(value="/city")
	@ResponseBody
	public List<City> getCity(){
		return cityMapper.getAll();
	}

}
