package com.xiudu.blog.pojo.DTO.blog;

import com.xiudu.blog.util.Validator.allNotEmpty.AllNotEmpty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

/**
 * @author: 锈渎
 * @date: 2024/1/31 19:05
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用来接收校验 添加和修改 的博客参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@AllNotEmpty(fields = {"contentHtml", "contentMarkdown"}, message = "请编写博客内容", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
public class BlogDTO {

    @NotNull(message = "请选择要修改的博客", groups = {BlogUpdateGroup.class})
    @Min(value = 1, message = "博客id异常", groups = {BlogUpdateGroup.class})
    private Long id;

    @NotBlank(message = "请输入标题", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    @Length(max = 20, message = "标题太长", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    private String title;

    @NotBlank(message = "请上传首图", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    @URL(message = "首图地址错误", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    private String firstPicture;

    private Long views;
    private Boolean top;
    private Boolean published;
    private Boolean comment;
    private String overview;

    @NotNull(message = "请选择分类", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    @Positive(message = "请选择分类", groups = {BlogUpdateGroup.class, BlogCreateGroup.class})
    private Long typeId;

    private String contentHtml;
    private String contentMarkdown;

}
