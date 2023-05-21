package com.sora.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan("com.sora.**")
public class SoraSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraSystemApplication.class, args);
    }
}
