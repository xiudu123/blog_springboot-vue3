package com.xiudu.blog.config.interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 锈渎
 * @date: 2023/12/9 20:56
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 全局配置前后端跨域问题
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 重新跨域支持方法
     * CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // addMapping 添加可跨域的请求地址
        registry.addMapping("/**")
                // 设置跨域 域名权限 规定由某一个指定的域名 + 端口能访问跨域项目
                .allowedOrigins("*")
                // 是否开启 cookie 跨域
                .allowCredentials(false)
                // 规定能够跨域访问的方法类型
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                // 添加验证头信息 token
                // .allowedHeaders()
                // 预检请求存活时间 在此期间不再次发送预检请求
                .maxAge(3600);
    }

    /**
     * 给静态资源添加映射
     * ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 开启 sa-token 的鉴权
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/authorize/**");

    }

}
