package com.fate.spring.zk.service;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fate.spring.zk.config.ZkConfig;
import com.fate.spring.zk.distributedlock.CuratorUtil;

/**
 * 基础的线程测试类
 * 
 * @author maijinchao
 *
 * @date 2020/09/24
 */
public class BaseZkTest implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BaseZkTest.class);

    /**
     * zk 锁的工具
     * 
     */
    protected CuratorUtil curator;

    /**
     * 线程数
     */
    public static final int NUM = 10;

    /**
     * 发射器
     */
    public static CountDownLatch cdl = new CountDownLatch(NUM);

    protected ZkConfig config;

    /**
     * 业务办理
     */
    public void busProcessing() {

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            cdl.await();
            busProcessing();
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("", e);
        }
    }

}
