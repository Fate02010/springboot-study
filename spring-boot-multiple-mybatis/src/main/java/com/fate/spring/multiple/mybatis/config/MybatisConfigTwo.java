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
@MapperScan(basePackages = "com.fate.spring.multiple.mybatis.mapper.world",sqlSessionTemplateRef = "worldSqlSessionTemplate")
public class MybatisConfigTwo {
	
	
	@Bean(name = "worldDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.two")
	public DataSource worldDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "worldSqlSessionFactory")
	public SqlSessionFactory worldSqlSessionFactory(@Qualifier("worldDataSource") DataSource dataSource) throws Exception {
		 SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	     bean.setDataSource(dataSource);
	     bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/world/*.xml"));
	     return bean.getObject();
	}
	
	
	@Bean(name = "worldTransactionManager")
	public DataSourceTransactionManager worldTransactionManager(@Qualifier("worldDataSource") DataSource dataSource) {
		 return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "worldSqlSessionTemplate")
    public SqlSessionTemplate worldSqlSessionTemplate(@Qualifier("worldSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
