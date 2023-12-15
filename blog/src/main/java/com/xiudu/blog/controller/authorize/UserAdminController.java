package com.xiudu.blog.controller.authorize;

import cn.dev33.satoken.stp.StpUtil;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.User;
import com.xiudu.blog.service.UserService;
import com.xiudu.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 锈渎
 * @date: 2023/12/15 14:53
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */

@RestController
@RequestMapping("/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = MD5Utils.code(user.getPassword());

        if("".equals(username) || username == null) {
            return Result.error("用户名不能为空");
        }
        if("".equals(password) || password == null) {
            return Result.error("密码不能为空");
        }
        user = userService.checkUser(username, password);
        if(user == null) {
            return Result.error("用户名或者密码错误");
        }
        StpUtil.login(user.getId());
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success("退出登录成功");
    }

}
