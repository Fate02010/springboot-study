package com.fate.spring.zk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * zk 的配置
 * 
 * @author maijinchao
 *
 * @date 2020/09/24
 */
@Configuration
@ConfigurationProperties(prefix = "zkserver")
public class ZkConfig {

    /**
     * zk 连接服务地址
     */
    private String address = "localhost:2181";

    /**
     * zk 锁的目录
     */
    private String lockPath = "/curator/lock";

    /**
     * 重试次数
     */
    private int retryCount = 3;

    /**
     * 重试的时间间隔,默认两秒
     */
    private int timeInterval = 2;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLockPath() {
        return lockPath;
    }

    public void setLockPath(String lockPath) {
        this.lockPath = lockPath;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

}
