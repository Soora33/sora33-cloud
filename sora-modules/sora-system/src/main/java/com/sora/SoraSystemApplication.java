package com.sora;

import com.sora.anno.SoraCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SoraCloudConfig
@SpringBootApplication
public class SoraSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraSystemApplication.class, args);
    }
}
