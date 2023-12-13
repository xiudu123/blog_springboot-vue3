package com.xiudu.service.impl;

import com.xiudu.blog.mapper.UserMapper;
import com.xiudu.blog.pojo.User;
import com.xiudu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
       return userMapper.selectByUsernameAndPassword(username, password);
    }
}
