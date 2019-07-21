package com.fate.spring.mybatis.sql.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.fate.spring.mybatis.sql.datasoure.DynamicDataSource;

/**
 * 
 * 数据源配置
 * @author maijinchao
 *
 */
@Configuration
public class DataSourceConfig {
	
	    // 多数据源的参数配置
	    @Value("${spring.datasource.default.url}")
	    private String defaultDBUrl;
	    @Value("${spring.datasource.default.username}")
	    private String defaultDBUser;
	    @Value("${spring.datasource.default.password}")
	    private String defaultDBPassword;
	    @Value("${spring.datasource.default.driver-class-name}")
	    private String defaultDBDreiverName;

	    @Value("${spring.datasource.master.url}")
	    private String masterDBUrl;
	    @Value("${spring.datasource.master.username}")
	    private String masterDBUser;
	    @Value("${spring.datasource.master.password}")
	    private String masterDBPassword;
	    @Value("${spring.datasource.default.driver-class-name}")
	    private String masterDBDreiverName;

	    @Bean
	    public DynamicDataSource dynamicDataSource() {
	    	// 设置多数据源的参数
	        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
	        DruidDataSource defaultDataSource = new DruidDataSource();
	        defaultDataSource.setUrl(defaultDBUrl);
	        defaultDataSource.setUsername(defaultDBUser);
	        defaultDataSource.setPassword(defaultDBPassword);
	        defaultDataSource.setDriverClassName(defaultDBDreiverName);

	        DruidDataSource masterDataSource = new DruidDataSource();
	        masterDataSource.setDriverClassName(masterDBDreiverName);
	        masterDataSource.setUrl(masterDBUrl);
	        masterDataSource.setUsername(masterDBUser);
	        masterDataSource.setPassword(masterDBPassword);

	        Map<Object,Object> map = new HashMap<>();
	        map.put("default", defaultDataSource);
	        map.put("master", masterDataSource);
	        dynamicDataSource.setTargetDataSources(map);
	        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

	        return dynamicDataSource;
	    }

	    /**
	     * SqlSessionFactory
	     * @param dynamicDataSource
	     * @return
	     * @throws Exception
	     */
	    @Bean
	    public SqlSessionFactory sqlSessionFactory(
	            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
	            throws Exception {
	        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	        bean.setDataSource(dynamicDataSource);
	        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
	                .getResources("classpath*:mapper/*.xml"));
	        return bean.getObject();

	    }

	    /**
	     * 创建 SqlSessionTemplate
	     * @param sqlSessionFactory
	     * @return
	     * @throws Exception
	     */
	    @Bean(name = "sqlSessionTemplate")
	    public SqlSessionTemplate sqlSessionTemplate(
	            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
	            throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
