package com.sora.anno;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname SoraCloudConfig
 * @Description 微服务公用注解
 * @Date 2023/06/02 23:23
 * @Author by Sora33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan(basePackages = {"com.sora.**"})
@EnableFeignClients
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public @interface SoraCloudConfig {
}
