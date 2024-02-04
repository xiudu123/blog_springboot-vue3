package com.xiudu.blog.pojo.DTO.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 锈渎
 * @date: 2024/2/2 14:13
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于接收校验评论的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @NotBlank(message = "请输入评论")
    private String content;
    @NotBlank(message = "请填写你的名称")
    private String nickname;
    @NotBlank(message = "请填写邮箱")
    @Email(message = "请正确填写邮箱地址")
    private String email;

    private String avatar;

    private Long parentId;
    private Long topCommentId;
    private Long blogId;
    private Long userId;

}
