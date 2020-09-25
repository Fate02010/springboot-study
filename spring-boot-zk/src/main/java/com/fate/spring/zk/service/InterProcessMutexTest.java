package com.fate.spring.zk.service;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fate.spring.zk.config.ZkConfig;

/**
 * InterProcessMutex 锁可重入排他锁
 * 
 * @author maijinchao
 *
 * @date 2020/09/24
 */

public class InterProcessMutexTest extends BaseZkTest {

    private static final Logger logger = LoggerFactory.getLogger(InterProcessMutexTest.class);

    public InterProcessMutexTest(ZkConfig config) {
        this.config = config;
    }

    public void busProcessing() {
        InterProcessMutex mutex = new InterProcessMutex(curator.getClient(), config.getLockPath());
        try {
            if (mutex.acquire(2, TimeUnit.SECONDS)) {
                logger.info("线程ID: {} ---获取锁成功", Thread.currentThread().getId());
                Thread.sleep(5000);
                // 释放锁
                mutex.release();
            } else {
                logger.info("获取锁失败");
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("", e);
        }
    }

}
