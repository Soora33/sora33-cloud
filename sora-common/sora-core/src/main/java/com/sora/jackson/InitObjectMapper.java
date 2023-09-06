package com.sora.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitObjectMapper {

    private static class Handler{
        // 如果序列化的对象为空 不会出现异常
        private static final ObjectMapper INSTANCE = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }


    @Bean(name = "ObjectMapperService")
    public static ObjectMapper initObjectMapper() {
        // 序列化时忽略空字段
        return Handler.INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
