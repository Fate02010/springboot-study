package com.fate.spring.zk.service;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fate.spring.zk.config.ZkConfig;

/**
 * 读锁
 * 
 * @author maijinchao
 *
 * @date 2020/09/24
 */
public class ReadLockTest extends BaseZkTest {

    private static final Logger logger = LoggerFactory.getLogger(ReadLockTest.class);

    public ReadLockTest(ZkConfig config) {
        this.config = config;
    }

    public void busProcessing() {
        InterProcessReadWriteLock mutex = new InterProcessReadWriteLock(curator.getClient(), config.getLockPath());
        InterProcessLock readLock = mutex.readLock();
        try {
            readLock.acquire();
            logger.info("线程ID: {} ---获取读锁成功", Thread.currentThread().getId());
            Thread.sleep(5000);
            // 释放锁
            readLock.release();
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("", e);
        } finally {
            curator.getClient().close();
        }
    }

}
