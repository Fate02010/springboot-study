package com.fate.spring.demo.threadpool.components;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程
 * 
 * @author maijinchao
 *
 * @date 2019/09/26
 */
@Slf4j
public class ThreadHandler implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            log.info("线程在处理");
            Thread.sleep(500);
            log.info("线程处理完毕");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
