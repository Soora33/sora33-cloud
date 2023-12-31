package com.sora;

import com.sora.anno.SoraCloudConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname SoraSpiderApplication
 * @Description spider启动类
 * @Date 2023/11/23 16:26
 * @Author by Sora33
 */
@MapperScan("com.sora.mapper.**")
@SpringBootApplication
@SoraCloudConfig
public class SoraSpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoraSpiderApplication.class, args);
    }
}
