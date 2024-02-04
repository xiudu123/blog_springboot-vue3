package com.xiudu.blog.config.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 锈渎
 * @date: 2023/12/10 15:29
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 封装统一接口返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private long timestamp; // 响应时间(当前毫秒数)
    private Integer status; // 返回状态码
    private String error; // 返回错误信息
    private Object data; // 返回数据类型

    public static Result<?> success() {
        return new Result<>(System.currentTimeMillis(), 200, "success", null);
    }

    public static Result<?> success(Object data) {
        return new Result<>(System.currentTimeMillis(),200, "success", data);
    }

    public static Result<?> error(String message) {
        return new Result<>(System.currentTimeMillis(),500, message, null);
    }

    public static Result<?> error(Integer code, String message) {
        return new Result<>(System.currentTimeMillis(),code, message, null);
    }

    public static Result<?> error() {
        return new Result<>(System.currentTimeMillis(),500, "fail", null);
    }

    public static Result<?> error(ResultStatus resultStatus) {
        return new Result<>(System.currentTimeMillis(), resultStatus.getStatus(), resultStatus.getMessage(), null);
    }
    public static Result<?> error(ResultStatus resultStatus, Object data) {
        return new Result<>(System.currentTimeMillis(), resultStatus.getStatus(), resultStatus.getMessage(), data);
    }
}
