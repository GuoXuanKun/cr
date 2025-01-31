package com.example.cr.user.config;

import com.example.cr.common.interceptor.UserInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 * 该类用于配置 Spring MVC 相关的拦截器，并注册 `UserInterceptor`。
 */
@Configuration  // 标注为 Spring 配置类，使其在 Spring 容器初始化时生效
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 注入 `UserInterceptor` 用户拦截器
     * 通过 `@Resource` 注解让 Spring 自动注入 `UserInterceptor` 实例
     */
    @Resource
    UserInterceptor userInterceptor;

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加用户拦截器 `UserInterceptor`
        registry.addInterceptor(userInterceptor)
                // 拦截所有路径（/** 表示拦截所有请求）
                .addPathPatterns("/**")
                // 下面这些路径不拦截，通常用于放行公共接口，如登录、注册等
                .excludePathPatterns(
                        "/test",        // 测试接口
                        "/register",    // 用户注册接口
                        "/send-code",   // 发送验证码接口
                        "/login"        // 登录接口
                );
    }
}
