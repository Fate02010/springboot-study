package com.fate.spring.jndi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fate.spring.jndi.biz.JndiBiz;
import com.fate.spring.jndi.entity.User;

@Controller
public class JndiController {

    @Autowired
    private JndiBiz biz;

    @RequestMapping("/all")
    @ResponseBody
    public String find() {
        return "" + biz.getUserCount();
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(User user) {
        return "hello " + biz.addUser(user);
    }

}
