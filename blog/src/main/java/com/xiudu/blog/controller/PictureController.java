package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: 锈渎
 * @date: 2023/12/30 14:25
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@RestController
@RequestMapping("/upload/picture")
public class PictureController {

    @PostMapping("/markdown")
    public Result<?> editorMd (@RequestBody MultipartFile file) throws Exception{
        System.out.println(file);
        String trueFileName = file.getOriginalFilename();
        assert trueFileName != null;
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-","")+suffix;


        String path = ResourceUtils.getURL("classpath:").getPath();
        File targetFile = new File(path,"static/img/upload/" + fileName);


        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //这就是返回给页面的json数据
//        res.put("url","http://127.0.0.1:3000"+"/images/upload/"+fileName);
//        res.put("success", 1);
//        res.put("message", "upload success!");

        return Result.success( "http://127.0.0.1:3000"+"/img/upload/"+fileName);

    }

}
