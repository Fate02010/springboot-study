package com.fate.spring.mybatis.sql.datasoure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态加载数据库
 * @author maijinchao
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	 private static DynamicDataSource instance;
	    private static byte[] lock=new byte[0];
	    private static Map<Object,Object> dataSourceMap=new HashMap<Object, Object>();

	    @Override
	    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
	        super.setTargetDataSources(targetDataSources);
	        dataSourceMap.putAll(targetDataSources);
	        super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
	    }

	    public Map<Object, Object> getDataSourceMap() {
	        return dataSourceMap;
	    }

	    public static synchronized DynamicDataSource getInstance(){
	        if(instance==null){
	            synchronized (lock){
	                if(instance==null){
	                    instance=new DynamicDataSource();
	                }
	            }
	        }
	        return instance;
	    }
	    //必须实现其方法
	    protected Object determineCurrentLookupKey() {
	        return DataSourceContextHolder.getDBType();
	    }
}
