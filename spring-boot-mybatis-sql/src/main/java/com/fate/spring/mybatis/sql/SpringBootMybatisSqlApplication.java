package com.fate.spring.mybatis.sql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.fate.spring.mybatis.sql.mapper")
@SpringBootApplication
public class SpringBootMybatisSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisSqlApplication.class, args);
	}

}
