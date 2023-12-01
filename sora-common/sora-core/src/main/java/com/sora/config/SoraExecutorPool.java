package com.sora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Classname SoraExecutorPool
 * @Description 公共线程池
 * @Date 2023/11/29 09:52
 * @Author by Sora33
 */
@Configuration
public class SoraExecutorPool {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8 + 1, 20, 300, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(128), new ThreadPoolExecutor.CallerRunsPolicy());

    @Bean
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return THREAD_POOL_EXECUTOR;
    }
}
