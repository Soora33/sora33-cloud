package com.sora;

import com.sora.anno.SoraCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname SoraAuthApplication
 * @Description 鉴权启动类
 * @Date 2023/05/21 16:06
 * @Author by Sora33
 */
@SoraCloudConfig
@SpringBootApplication
public class SoraAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraAuthApplication.class, args);
    }
}
