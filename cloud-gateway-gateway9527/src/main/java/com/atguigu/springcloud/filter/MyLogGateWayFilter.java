package com.atguigu.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Date;

@Component
public class MyLogGateWayFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("time" + new Date() +"\t 执行了自定义全局过滤器" + "MyLogGateWayFilter" + "hello" );
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if (username == null){
            System.out.println("***用户名为null，无法登陆");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        else {
            System.out.println("***用户名存在，允许登陆");
        }
        return chain.filter(exchange);
    }
}
