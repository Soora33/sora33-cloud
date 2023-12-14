package com.sora.user;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.sora.anno.UserLogAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Classname SaveUserLog
 * @Description 记录用户操作
 * @Date 2023/11/27 14:23
 * @Author by Sora33
 */
@Component
@Aspect
public class UserLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserLogAspect.class);

    private final UserLogFeign userLogFeign;

    public UserLogAspect(UserLogFeign userLogFeign) {
        this.userLogFeign = userLogFeign;
    }


    /**
     * 保存用户操作到数据库对应用户日志表
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "@annotation(com.sora.anno.UserLogAnno)")
    public Object saveUserLogToDB(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object result = proceedingJoinPoint.proceed();
            sw.stop();
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            // 方法名
            String methodName = method.getName();
            // 类名
            String className = proceedingJoinPoint.getTarget().getClass().getName();
            // 获取当前用户id
            UserLogAnno userLogAnno = method.getAnnotation(UserLogAnno.class);
            Class<?> servletUtils = Class.forName("com.sora.utils.ServletUtils");
            Object servletUtilInstance = servletUtils.newInstance();
            Method getUserIdByTokenMethod = servletUtils.getDeclaredMethod("getUserIdByToken");
            Object userId = getUserIdByTokenMethod.invoke(servletUtilInstance);
            if (!StrUtil.isBlankIfStr(userId)) {
                UserLog userLog = new UserLog(IdUtil.getSnowflakeNextIdStr(), userId.toString(),
                        userLogAnno.type(), className, methodName, userLogAnno.description(), sw.getLastTaskTimeMillis(), new Date());
                // 加入到日志表
                userLogFeign.insert(userLog);
            }
            return result;
        } catch (Exception e) {
            logger.error("[错误日志],保存用户操作记录失败！", e);
        } catch (Throwable e) {
            logger.error("[错误日志],记录用户日志切面运行发生异常",e);
        }
        return null;
    }
}
