package com.sora.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Classname ReadOutFile
 * @Description
 * @Date 2023/06/22 10:46
 * @Author by Sora33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@PropertySource(value = "classpath:soraInfo.properties", name = "sora-data-properties")
public class ReadOutFile {

    @Value("${sora.name}")
    private String soraName;
    @Value("${sakura.name}")
    private String sakuraName;
    @Value("${nayuta.type}")
    private String nayutaType;
}
