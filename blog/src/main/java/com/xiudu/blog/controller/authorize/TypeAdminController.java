package com.xiudu.blog.controller.authorize;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.config.api.ResultStatus;
import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.DTO.type.TypeCreateGroup;
import com.xiudu.blog.pojo.DTO.type.TypeDTO;
import com.xiudu.blog.pojo.DTO.type.TypeUpdateGroup;
import com.xiudu.blog.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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

    @Operation(summary = "搜索分类", description = "根据分类名字模糊查询")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页数", required = false),
            @Parameter(name = "typeName", description = "分类名字", required = true)
    })
    @GetMapping("/get/search")
    public Result<?> getTypeSearch(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam String typeName) {
        return Result.success(typeService.listTypeAndSearch(pageNum, typeName));
    }

    @Operation(summary = "搜索分类", description = "查询所有的分类")
    @GetMapping("/get/all")
    public Result<?> getTypeAll() {
        return Result.success(typeService.listTypeAll());
    }

    @Operation(summary = "添加分类", description = "添加分类")
    @Parameters({
            @Parameter(name = "type", description = "分类类", required = true)
    })
    @PostMapping("/add")
    public Result<?> addType(@Validated(TypeCreateGroup.class) @RequestBody TypeDTO typeDTO) {
        Type type = new Type().setName(typeDTO.getName());

        int successInsert = typeService.insertType(type);
        if(successInsert == 0) return Result.error(ResultStatus.INSERT_ERROR_TYPE);
        else return Result.success();
    }

    @Operation(summary = "修改分类", description = "修改分类")
    @Parameters({
            @Parameter(name = "newType", description = "添加的分类", required = true)
    })
    @PostMapping("/update")
    public Result<?> updateType(@Validated(TypeUpdateGroup.class) @RequestBody TypeDTO newTypeDTO) {

        Type type = new Type()
                .setId(newTypeDTO.getId())
                .setName(newTypeDTO.getName());

        int successUpdate = typeService.updateType(type);
        if(successUpdate == 0) return Result.error(ResultStatus.UPDATE_ERROR_TYPE);
        else return Result.success();
    }

    @Operation(summary = "删除分类", description = "根据分类Id删除分类")
    @Parameters({
            @Parameter(name = "typeId", description = "分类Id", required = true)
    })
    @PostMapping("/delete")
    public Result<?> deleteType(@RequestBody Long typeId) {
        Type type = typeService.getType(typeId);
        if(type == null) {
            return Result.error(ResultStatus.NOT_FOUND_TYPE);
        }
        if(type.getCount() > 0) {
            return Result.error(ResultStatus.DELETE_NOT_ALLOW_TYPE);
        }

        int successDelete = typeService.deleteType(typeId);
        if(successDelete == 0) return Result.error(ResultStatus.DELETE_ERROR_TYPE);
        else return Result.success();
    }
}
