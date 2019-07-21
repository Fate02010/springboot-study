package com.fate.spring.mybatis.sql.datasoure;

/**
 * 数据源上下文
 * @author maijinchao
 *
 */
public class DataSourceContextHolder {
	 private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	    public static synchronized void setDBType(String dbType){
	        contextHolder.set(dbType);
	    }

	    public static String getDBType(){
	        return contextHolder.get();
	    }

	    public static void clearDBType(){
	        contextHolder.remove();
	    }
}
