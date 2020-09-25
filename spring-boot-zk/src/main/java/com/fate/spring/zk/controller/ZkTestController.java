package com.fate.spring.zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fate.spring.zk.config.ZkConfig;
import com.fate.spring.zk.service.InterProcessMutexTest;
import com.fate.spring.zk.service.ReadLockTest;
import com.fate.spring.zk.service.WriteLockTest;

@RestController
@RequestMapping("zk")
public class ZkTestController {



    @Autowired
    private ZkConfig config;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String testMutex(@RequestParam("mode") String mode) {
        for (int i = 0; i < InterProcessMutexTest.NUM; i++) {
            if (mode.equals("0")) {
                new Thread(new InterProcessMutexTest(config)).start();
                InterProcessMutexTest.cdl.countDown();
            }
            if (mode.equals("1")) {
                new Thread(new ReadLockTest(config)).start();
                ReadLockTest.cdl.countDown();
            }
            if (mode.equals("2")) {
                new Thread(new WriteLockTest(config)).start();
                WriteLockTest.cdl.countDown();

            }

        }
        return "ok";
    }


}
