package com.sora.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Classname ServiceLink
 * @Description 服务之间强制通信
 * @Date 2023/11/28 14:32
 * @Author by Sora33
 */
@Component
public class ServiceLink {

    private final HashMap<String, String> headerMap = new HashMap<>() {{
        put("token", "123");
    }};


    public String serviceLink(String serviceName, String path, boolean isGet) {
        // 获取到对应微服务信息
        LoadBalancerClient loadBalancerClient = SpringUtil.getBean(LoadBalancerClient.class);
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        // 拼接请求url
        String url = "http://" + host + ":" + port + path;
        String response;
        if (isGet) {
            response = OkHttpUtils.get(url, null, headerMap);
        } else {
            response = OkHttpUtils.post(url, null, headerMap);;
        }
        return response;

    }
}
