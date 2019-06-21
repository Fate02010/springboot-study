package com.fate.spring.jndi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.fate.spring.jndi.mapper")
@SpringBootApplication
public class SpringBootJndiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootJndiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJndiApplication.class, args);
    }

}
