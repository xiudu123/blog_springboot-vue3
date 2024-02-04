package com.xiudu.blog.config.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum ResultStatus {


    SUCCESS(200, "success"),
    ERROR(500, "系统错误"),
    RUNTIME_ERROR(500, "运行错误"),

    // 用户
    LOGIN_CHECK_ERROR(2001, "账号或密码错误"),
    NO_LOGIN(2002, "请登录"),
    LOGIN_EXPIRED(2003, "登录已过期，请重新登录"),
    NO_PERMISSION(2004, "权限不足"),

    // 增
    INSERT_ERROR_BLOG(3001, "新增博客失败，请稍后再试"),
    INSERT_ERROR_TYPE(3002, "新增分类失败，请稍后再试"),
    INSERT_ERROR_COMMENT(3003, "评论失败，请稍后再试"),
    // 删
    DELETE_ERROR_BLOG(4001, "删除博客失败，请稍后再试"),
    DELETE_ERROR_TYPE(4002, "删除分类失败，请稍后再试"),
    DELETE_NOT_ALLOW_TYPE(4003, "该标签下还有博客，不允许删除"),
    // 改
    UPDATE_ERROR_BLOG(5001, "修改博客失败，请稍后再试"),
    UPDATE_ERROR_TYPE(5002, "修改分类失败，请稍后再试"),
    // 查
    NOT_FOUND_BLOG(6001, "该博客不存在或已被删除"),
    NOT_FOUND_TYPE(6002, "该标签不存在或已被删除"),
    EXIST_TYPE(6003, "该分类已存在"),



    VALID_ERROR(1000, "参数校验错误");


    private final Integer status;
    private final String message;
}
