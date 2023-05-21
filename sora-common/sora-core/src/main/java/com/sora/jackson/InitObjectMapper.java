package com.sora.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitObjectMapper {

    private static class Handler{
        private static final ObjectMapper INSTANCE = new ObjectMapper();
    }


    @Bean(name = "ObjectMapperService")
    public static ObjectMapper initObjectMapper() {
        return Handler.INSTANCE;
    }
}
