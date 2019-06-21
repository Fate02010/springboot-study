package com.fate.spring.jndi.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fate.spring.jndi.entity.User;
import com.fate.spring.jndi.mapper.UserMapper;

@Service
public class JndiBiz {

    @Autowired
    private UserMapper mapper;

    /**
     * 查询用户总数
     * 
     * @return
     */
    public int getUserCount() {
        return mapper.selectAll().size();
    }

    /**
     * 增加用户
     * 
     * @return
     */
    public int addUser(User user) {
        return mapper.insert(user);
    }
}
