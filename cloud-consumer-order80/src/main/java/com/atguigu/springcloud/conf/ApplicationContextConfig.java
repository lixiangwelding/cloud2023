package com.atguigu.springcloud.conf;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced //注解赋予RestTemplate负载均衡能力
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
