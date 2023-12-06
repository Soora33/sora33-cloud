package com.sora;

import com.sora.anno.SoraCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SoraCloudConfig
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SoraGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraGatewayApplication.class, args);
    }
}
