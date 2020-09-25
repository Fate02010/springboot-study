package com.fate.spring.zk.distributedlock;

import javax.annotation.PostConstruct;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fate.spring.zk.config.ZkConfig;

/**
 * 分布式锁的工具
 * 
 * @author maijinchao
 *
 * @date 2020/09/24
 */
@Component
public class CuratorUtil {

    private static CuratorUtil util;

    /**
     * zk 配置
     */
    @Autowired
    private ZkConfig config;

    private CuratorFramework client;

    /**
     * 初始化参数
     */
    @PostConstruct
    private void init() {
        util = this;
        // 1、重试策略：初试时间为1s 重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(config.getTimeInterval(), config.getRetryCount());
        // 2、通过工厂创建连接
        client = CuratorFrameworkFactory.newClient(config.getAddress(), retryPolicy);
        // 3、开启连接
        client.start();
    }


    /**
     * 获取锁的客户端
     * 
     * @return
     */
    public static CuratorFramework getClient() {
        return util.client;
    }

}
