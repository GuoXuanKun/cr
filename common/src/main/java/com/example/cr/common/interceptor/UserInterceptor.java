package com.example.cr.common.interceptor;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.cr.common.context.UserContext;
import com.example.cr.common.response.LoginResponse;
import com.example.cr.common.util.CustomJWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户拦截器 (UserInterceptor)
 * 该拦截器用于拦截所有 HTTP 请求，解析请求头中的 JWT 令牌（Authorization: Bearer xxx），
 * 并将解析出的用户信息存入 UserContext，以便在后续的请求处理中使用。
 */
@Component  // 声明为 Spring 组件，使其能够被 Spring 容器管理
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class); // 日志记录器

    /**
     * 在请求到达控制器（Controller）之前执行的逻辑
     *
     * @param request  HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象
     * @param handler  目标处理器（Controller 方法）
     * @return boolean  返回 true 继续执行请求，返回 false 拦截请求
     * @throws Exception 可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("UserInterceptor 开始"); // 记录拦截器的执行日志

        // 1. 获取请求头中的 Authorization 字段，通常用于存储 JWT 令牌
        String token = request.getHeader("Authorization");

        // 2. 判断请求头是否包含 token
        if (StrUtil.isNotBlank(token)) { // 使用 Hutool 工具库判断 token 是否为空
            // 3. 去掉 "Bearer " 前缀，只获取实际的 JWT 令牌
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 占 7 个字符，所以截取掉前 7 个字符
            }
            log.info("当前请求头中包含的 token = {}", token); // 记录解析后的 token

            // 4. 解析 JWT 令牌，获取用户信息
            JSONObject loginUser = CustomJWTUtils.getJSONObject(token);
            log.info("当前登录会员 = {}", loginUser); // 记录解析出的用户信息

            // 5. 将 JSON 数据转换为 LoginResponse 对象
            LoginResponse user = JSONUtil.toBean(loginUser, LoginResponse.class);

            // 6. 将用户信息存入 ThreadLocal 变量，方便后续请求中获取用户信息
            UserContext.setUser(user);
        }

        log.info("UserInterceptor 结束"); // 记录拦截器的结束日志
        return true; // 返回 true，继续执行请求
    }
}