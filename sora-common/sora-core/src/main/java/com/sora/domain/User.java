package com.sora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname User
 * @Description 用户
 * @Date 2023/05/21 20:48
 * @Author by Sora33
 */
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
public class User {

    private Long userId;
    private String name;
    private Integer age;

}
