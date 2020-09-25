package com.fate.spring.zk.service;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fate.spring.zk.config.ZkConfig;

public class WriteLockTest extends BaseZkTest {

    private static final Logger logger = LoggerFactory.getLogger(WriteLockTest.class);

    public WriteLockTest(ZkConfig config) {
        this.config = config;
    }

    public void busProcessing() {
        InterProcessReadWriteLock mutex = new InterProcessReadWriteLock(curator.getClient(), config.getLockPath());
        InterProcessLock writeLock = mutex.writeLock();
        try {
            writeLock.acquire();
            logger.info("线程ID: {} ---获取写锁成功", Thread.currentThread().getId());
            Thread.sleep(3000);
            // 释放锁
            writeLock.release();
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("线程ID: {} ---获取写锁失败", Thread.currentThread().getId());
            logger.error("", e);
        } finally {
            // curator.getClient().close();
        }
    }

}
