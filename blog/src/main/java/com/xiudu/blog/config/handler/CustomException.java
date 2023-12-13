package com.xiudu.blog.config.handler;

import lombok.Getter;

/**
 * @author: 锈渎
 * @date: 2023/12/10 18:32
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 自定义错误
 */

@Getter
public class CustomException extends RuntimeException{
    private final Integer code;
    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
        this.code = 5;
    }
}
