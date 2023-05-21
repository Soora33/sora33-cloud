package com.sora.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.sora.constant.JwtConstants;
import com.sora.gateway.config.IgnoreWhiteConfig;
import com.sora.gateway.util.GatewayUtil;
import com.sora.redis.util.RedisUtil;
import com.sora.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @description: 鉴权过滤器
 */
@Component
@Log4j2
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 白名单
     */
    private static Set<String> ignore;

    /**
     * redis
     */
    private final RedisUtil redisUtil;

    public AuthFilter(IgnoreWhiteConfig ignoreWhite, RedisUtil redisUtil) {
        AuthFilter.ignore = ignoreWhite.getWhites();
        this.redisUtil = redisUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求作用域
        ServerHttpRequest request = exchange.getRequest();
        // 请求头
        HttpHeaders headers = request.getHeaders();
        // 获取请求方式
        HttpMethod method = request.getMethod();
        // header操作对象
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (GatewayUtil.containsSubstring(ignore,url)) {
            return chain.filter(exchange);
        }
        log.info("请求日志：uri:[{}] , 请求方式:[{}]", url, method);
        if (StrUtil.isBlank(headers.getFirst(JwtConstants.TOKEN))) {
            return GatewayUtil.gatewayError(exchange,"");
        }
        String token = headers.getFirst(JwtConstants.TOKEN);
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            return GatewayUtil.gatewayError(exchange,"令牌已过期或验证不正确!");
        }
        String userId = JwtUtils.getUserId(claims);
        boolean isLogin = redisUtil.hasKey(JwtConstants.TOKEN_USER_PREFIX + userId);
        if (!isLogin) {
            return GatewayUtil.gatewayError(exchange,"登陆状态已过期!");
        }
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }


}