package com.xiudu.blog.pojo.DTO.type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 锈渎
 * @date: 2024/2/1 20:56
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于接收校验 添加和修改 标签的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    @NotNull(message = "请选择要修改的分类", groups = {TypeUpdateGroup.class})
    @Min(value = 1, message = "分类id异常")
    private Long id;
    @NotBlank(message = "请输入分类名称", groups = {TypeUpdateGroup.class, TypeCreateGroup.class})
    private String name;
}
