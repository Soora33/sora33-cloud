package com.sora;

import com.sora.anno.SoraCloudConfig;
import com.sora.utils.RabbitMqUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SoraCloudConfig
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {
                RabbitMqUtils.class})})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class})
public class SoraGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoraGatewayApplication.class, args);
    }
}
