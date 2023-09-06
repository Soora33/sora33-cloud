package com.sora.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * @Classname SoraPropetySourceLocator
 * @Description 自定义属性
 * @Date 2023/06/22 13:27
 * @Author by Sora33
 */
@Log4j2
public class SoraPropertySourceLocator implements PropertySourceLocator {


    @Override
    public PropertySource<?> locate(Environment environment) {
        final String fileName = "soraInfo.yml";
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
        try {
            // 指定外部文件的 属性名和文件名
            List<PropertySource<?>> propertySources = sourceLoader.load("sora-data-yml",
                    new ClassPathResource(fileName));
            // 获取第一个元素
            PropertySource<?> propertySource = propertySources.get(0);
            return propertySource;
        } catch (IOException e) {
            log.info("yml文件未找到！");
        }
        return null;
    }
}
