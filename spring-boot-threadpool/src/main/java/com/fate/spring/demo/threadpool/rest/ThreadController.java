package com.fate.spring.demo.threadpool.rest;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fate.spring.demo.threadpool.components.HandlerThreadPool;
import com.fate.spring.demo.threadpool.components.ThreadHandler;

/**
 * 线程控制器
 * 
 * @author maijinchao
 *
 */
@RequestMapping("thread")
@RestController
public class ThreadController {

    @Autowired
    private HandlerThreadPool HandlerThreadPool;

    /**
     * 普通线程池接口
     * 
     * @return
     */
    @RequestMapping(value = "/normalthread", method = {RequestMethod.POST, RequestMethod.GET})
    public String normalThreadPool() {

        ThreadHandler threadHandler = new ThreadHandler();
        ThreadPoolExecutor thread = HandlerThreadPool.getThreadPoolExecutorInstance();
        thread.execute(threadHandler);
        return "success";
    }

}
