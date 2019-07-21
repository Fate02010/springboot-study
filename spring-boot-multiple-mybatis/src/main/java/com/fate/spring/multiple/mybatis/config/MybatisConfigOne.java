package com.fate.spring.multiple.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.fate.spring.multiple.mybatis.mapper.testdb",sqlSessionTemplateRef = "testSqlSessionTemplate")
public class MybatisConfigOne {
	
	
	@Bean(name = "testOneDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.one")
	@Primary
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "testSqlSessionFactory")
	@Primary
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("testOneDataSource") DataSource dataSource) throws Exception {
		 SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	     bean.setDataSource(dataSource);
	     bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/test/*.xml"));
	     return bean.getObject();
	}
	
	
	@Bean(name = "testTransactionManager")
    @Primary
	public DataSourceTransactionManager testTransactionManager(@Qualifier("testOneDataSource") DataSource dataSource) {
		 return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "testSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
