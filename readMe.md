重构博客项目，使其成为前后端分离

原项目地址：[xiudu123/xiu_blog: 基于springboot前后端不分离博客网站 (github.com)](https://github.com/xiudu123/xiu_blog) 



> 2023.12.12

添加了日志，解决了跨域问题，统一了接口封装和异常处理

> 2023.12.13

连接上了数据库，重构完了 `pojo` 层, `mapper` 层和`Service` 层

> 2023.12.14

解决了@Mapper注解失败的问题。

完成了公开接口的编写。

> 2023.12.15

完成非公开接口的编写, 并加上登录权限

> 2023.12.16

完成前端访客页面

> 2023.12.17

完成前端管理员界面

> 2023.12.18

完成博客界面

> 2023.12.19

实现登录以及页面访问权限的管理。

鉴权框架：`Sa-Token` 

> 2023.12.21

解决 `sa-Token` 前后端跨域找不到自定义请求头的问题