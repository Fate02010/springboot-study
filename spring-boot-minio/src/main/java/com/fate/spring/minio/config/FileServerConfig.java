package com.fate.spring.minio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件服务器配置
 * 
 * @author maijinchao
 *
 * @date 2020/09/17
 */
@Configuration
@ConfigurationProperties(prefix = "file.server")
public class FileServerConfig {

    /**
     * 文件服务器连接地址
     */
    private String url = "http://localhost:9000";

    /**
     * 用户名
     */
    private String user = "AKIAIOSFODNN7EXAMPLE";

    /**
     * 密码
     */
    private String pwd = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

    /**
     * 访问的盘符
     */
    private String bucket = "test";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
