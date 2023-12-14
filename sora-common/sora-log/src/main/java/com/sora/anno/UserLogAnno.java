package com.sora.anno;

import com.sora.constants.UserLogConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname UserLog
 * @Description 用户行为注解
 * @Date 2023/11/27 14:07
 * @Author by Sora33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLogAnno {
    String type() default UserLogConstants.DEFAULT;
    String description() default "/";
}
