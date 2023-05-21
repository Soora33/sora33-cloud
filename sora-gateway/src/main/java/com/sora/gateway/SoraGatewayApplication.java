package com.sora.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.sora.**")
@EnableFeignClients
public class SoraGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraGatewayApplication.class, args);
    }
}
