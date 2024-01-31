package com.xiudu.blog.controller.authorize;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.User;
import com.xiudu.blog.service.UserService;
import com.xiudu.blog.util.MD5Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        // 获取账号密码
        String username = user.getUsername();
        String password = user.getPassword();
        // 账号密码判空
        if("".equals(username) || username == null) {
            return Result.error("用户名不能为空");
        }
        if("".equals(password) || password == null) {
            return Result.error("密码不能为空");
        }

        // 密码进行 md5 加密
        password = MD5Utils.code(password);

        // 查询用户
        user = userService.checkUser(username, password);
        if(user == null) {
            return Result.error("用户名或者密码错误");
        }

        StpUtil.login(user.getId());
        user.setPassword(null);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", tokenInfo.tokenValue);
        return Result.success(result);
    }

    @Operation(summary = "检查登录是否过期", description = "过期报错，没过期返回用户")
    @GetMapping("/check")
    public Result<?> checkToken() {
        if(!StpUtil.isLogin()) {
            return Result.error(401, "登录已过期，请重新登录");
        }
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.selectUserById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @Operation(summary = "退出登录", description = "退出登录")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success("退出登录成功");
    }

}
