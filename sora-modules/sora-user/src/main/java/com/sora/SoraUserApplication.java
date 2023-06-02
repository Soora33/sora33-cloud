package com.sora;

import com.sora.anno.SoraCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname SoraUserApplication
 * @Description 用户模块启动类
 * @Date 2023/06/02 23:03
 * @Author by Sora33
 */
@SoraCloudConfig
@SpringBootApplication
public class SoraUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraUserApplication.class, args);
    }
}
