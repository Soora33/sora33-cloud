package com.sora.constant;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * @Classname AuthConstant
 * @Description 鉴权常量
 * @Date 2023/05/21 20:52
 * @Author by Sora33
 */
public class AuthConstant implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
        PropertySource<?> propertySources = null;
        try {
            List<PropertySource<?>> load = sourceLoader.load("ymlData",
                    new ClassPathResource("soraInfo.yml"));
            PropertySource<?> propertySource = load.get(0);
            return propertySource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
