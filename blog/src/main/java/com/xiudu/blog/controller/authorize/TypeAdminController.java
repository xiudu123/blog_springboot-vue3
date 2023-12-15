package com.xiudu.blog.controller.authorize;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Type;
import com.xiudu.blog.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2023/12/15 16:41
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@RestController
@RequestMapping("/authorize/types")
public class TypeAdminController {
    @Autowired
    private TypeService typeService;

    @Operation(summary = "添加分类", description = "添加分类")
    @PostMapping("/add")
    public Result<?> addType(@RequestBody Type type) {
        if ("".equals(type.getName()) || type.getName() == null) {
            return Result.error("请输入分类名字");
        }
        if(!typeService.isEmptyByTypeName(type.getName())) {
            return Result.error("该分类已存在");
        }

        Date date = new Date();
        type.setCreateTime(date);
        type.setUpdateTime(date);
        int successInsert = typeService.insertType(type);
        if(successInsert == 0) return Result.error("添加失败, 请稍后再试");
        else return Result.success();
    }

    @Operation(summary = "修改分类", description = "修改分类")
    @PostMapping("/update")
    public Result<?> updateType(@RequestBody Type newType) {
        Type oldType = typeService.getType(newType.getId());
        if(oldType == null) {
            return Result.error("该分类不存在或已被删除");
        }
        if("".equals(newType.getName()) || newType.getName() == null) {
            return Result.error("请输入该分类名称");
        }
        if(oldType.getName().equals(newType.getName())) {
            return Result.error("修改的分类不能与之前相同");
        }
        if(!typeService.isEmptyByTypeName(newType.getName())) {
            return Result.error("该分类已存在");
        }

        newType.setUpdateTime(new Date());
        int successUpdate = typeService.updateType(newType.getId(), newType);
        if(successUpdate == 0) return Result.error("修改失败， 请稍后再试");
        else return Result.success();
    }

    @Operation(summary = "删除分类", description = "删除分类")
    @PostMapping("/delete")
    public Result<?> deleteType(@RequestBody Long typeId) {
        if(typeService.getType(typeId) == null) {
            return Result.error("该类型不存在或已被删除");
        }
        int successDelete = typeService.deleteType(typeId);
        if(successDelete == 0) return Result.error("删除失败, 请稍后再试");
        else return Result.success();
    }
}
