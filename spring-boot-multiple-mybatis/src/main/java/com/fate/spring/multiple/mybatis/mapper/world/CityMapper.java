package com.fate.spring.multiple.mybatis.mapper.world;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.fate.spring.multiple.mybatis.entity.City;

public interface CityMapper {
	
	@Select("select * from city")
	List<City> getAll();

}
