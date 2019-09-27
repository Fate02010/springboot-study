package com.fate.spring.demo.threadpool.components;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * 创建线程池
 * 
 * @author maijinchao
 *
 * @date 2019/09/26
 */
@Component
public class HandlerThreadPool {

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool = null;

    /**
     * 获取线程池实例
     * 
     * @return
     */
    public ThreadPoolExecutor getThreadPoolExecutorInstance() {
        if (threadPool == null) {
            threadPool =
                new ThreadPoolExecutor(10, 200, 300, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(50));
        }
        return threadPool;
    }

}
