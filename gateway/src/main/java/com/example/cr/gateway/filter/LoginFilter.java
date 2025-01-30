package com.example.cr.gateway.filter;

import cn.hutool.json.JSONException;
import com.example.cr.common.util.CustomJWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoginFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 检查 http 请求头中是否带有 Authorization: Bearer token
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/test")
                || path.contains("/register")
                || path.contains("/send-code")
                || path.contains("/login")) {
            log.info("不需要登录验证 URL={}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // 截取 "Bearer " 后面的 token
        }
        if (token == null || token.isEmpty()) {
            log.info("token 为空，拦截该请求 {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        log.info("解析到请求头的 token={}", token);
        boolean verify = false;
        try {
            verify = CustomJWTUtils.validate(token);
        } catch (JSONException e) {
            log.warn("token : {}, 解析异常 : {}", token, e.getMessage());
        }
        // 检验 token 是否有效，是否过期
        if (!verify) {
            log.error("token 无效，请重新登录 {}", token);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
