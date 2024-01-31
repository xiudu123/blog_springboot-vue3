package com.xiudu.blog.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 锈渎
 * @date: 2024/1/31 19:05
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String firstPicture;
    private Long views;
    private Boolean top;
    private Boolean published;
    private Boolean comment;
    private String overview;
    private Long typeId;
    private String contentHtml;
    private String contentMarkdown;

}
