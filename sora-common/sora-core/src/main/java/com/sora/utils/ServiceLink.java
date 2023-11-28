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

    public void test(String serviceName, String path) {
        LoadBalancerClient loadBalancerClient = SpringUtil.getBean(LoadBalancerClient.class);
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = "http://" + host + ":" + port + path;
        String response = OkHttpUtils.get(url, new HashMap<>() {{
            put("token", "123");
        }});

        System.out.println(response);
    }
}
