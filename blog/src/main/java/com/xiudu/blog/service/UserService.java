package com.xiudu.blog.service;

import com.xiudu.blog.pojo.User;

/**
 * Created by 锈渎 on 2023/12/13 18:37
 */
public interface UserService {
    User checkUser(String username, String password);
}
