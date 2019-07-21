package com.fate.spring.mybatis.sql.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 通用 mapper
 * @author maijinchao
 *
 */
public interface CommonMapper {
	
	/**
	 * 如果想不用返回实体可以使用 List<LinkedHashMap<String, Object>> 返回值
	 * @param sqlString
	 * @return
	 */
	@Select("${sqlString}")
	List<LinkedHashMap<String, Object>> executeSql(@Param(value="sqlString")  String sqlString);
}
