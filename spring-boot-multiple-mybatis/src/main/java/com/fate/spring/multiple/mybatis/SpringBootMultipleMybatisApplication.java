package com.fate.spring.multiple.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.fate.spring.multiple.mybatis.mapper")
@SpringBootApplication
public class SpringBootMultipleMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleMybatisApplication.class, args);
	}

}
