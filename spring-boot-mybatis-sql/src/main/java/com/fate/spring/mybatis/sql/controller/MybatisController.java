package com.fate.spring.mybatis.sql.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fate.spring.mybatis.sql.datasoure.DataSourceContextHolder;
import com.fate.spring.mybatis.sql.mapper.CommonMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MybatisController {
	
	@Autowired
	private CommonMapper mapper;
	
	/**
	 * 
	 * 测试 mybatis 执行任意的 sql
	 * @return
	 */
	@RequestMapping(value="/selectsql", method = RequestMethod.GET)
	@ResponseBody
	public List<LinkedHashMap<String, Object>> selectSql(){
		return mapper.executeSql("select * from user");
	}
	
	/**
	 * 测试动态数据源查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/selectsqlbyparma", method = RequestMethod.GET)
	@ResponseBody
	public List<LinkedHashMap<String, Object>> selectSqlByParma(String id,String sql){
		 DataSourceContextHolder.setDBType(id);
		 log.info("获取当前数据源"+DataSourceContextHolder.getDBType());
		 return mapper.executeSql(sql);
	}

}
