package com.sora.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @description: 放行白名单配置
 */
@Configuration
@ConfigurationProperties(prefix = "ignore")
@Data
public class IgnoreWhiteConfig {

    private Set<String> whites;

    private Set<String> token;

}