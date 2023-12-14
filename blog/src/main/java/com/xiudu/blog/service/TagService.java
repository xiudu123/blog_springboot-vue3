package com.xiudu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.pojo.Tag;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface TagService {
    int insertTag(Tag tag);

    int deleteTag(Long id);

    int updateTag(Long id, Tag tag);

    Tag getTag(Long id);
    Page<Tag> listTag(Integer pageNum);
    Tag getTagByName(String name);
    List<Tag> listTagAll();


}
