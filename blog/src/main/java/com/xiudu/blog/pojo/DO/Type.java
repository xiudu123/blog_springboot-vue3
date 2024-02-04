package com.xiudu.blog.pojo.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2023/12/13 14:59
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 关联数据库 type 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Type {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name; // 分类名称
    private Long count; // 分类所属博客的数量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime; //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime; // 修改时间
}
