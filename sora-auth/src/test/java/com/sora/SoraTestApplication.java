package com.sora;

import com.sora.domain.User;
import com.sora.utils.async.executor.Async;
import com.sora.utils.async.wrapper.WorkerWrapper;
import com.sora.worker.Work1;
import com.sora.worker.Work2;
import com.sora.worker.Work3;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Classname SoraTestApplication
 * @Description
 * @Date 2023/05/25 20:31
 * @Author by Sora33
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SoraTestApplication {

    private final Logger logger = LoggerFactory.getLogger(SoraTestApplication.class);

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(9, 20, 300, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(512), new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Work1 work1 = new Work1();
        Work1 work2 = new Work1();
        Work1 work3 = new Work1();
        WorkerWrapper<String, Integer> workerWrapper3 = new WorkerWrapper.Builder<String, Integer>()
                .worker(work3)
                .callback(work3)
                .id("rd")
                .param("第三个任务")
                .build();
        WorkerWrapper<String, Integer> workerWrapper2 = new WorkerWrapper.Builder<String, Integer>()
                .worker(work2)
                .callback(work2)
                .id("nd")
                .param("第二个任务")
                .build();
        WorkerWrapper<String, Integer> workerWrapper = new WorkerWrapper.Builder<String, Integer>()
                .worker(work1)
                .callback(work1)
                .id("st")
                .param("第一个任务")
                .build();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Async.beginWork(10000, threadPool,workerWrapper,workerWrapper2,workerWrapper3);
        stopWatch.stop();
        logger.info("线程任务执行完成:共耗时[{}]MS", stopWatch.getLastTaskTimeMillis());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Work1 work1 = new Work1();
        Work2 work2 = new Work2();
        Work3 work3 = new Work3();
        HashMap<String, Object> hashMap = new HashMap<>();
        WorkerWrapper<HashMap<String, Object>, HashMap<String, Object>> workerWrapper3 = new WorkerWrapper.Builder<HashMap<String, Object>, HashMap<String, Object>>()
                .worker(work3)
                .callback(work3)
                .id("rd")
                .param(hashMap)
                .build();
        WorkerWrapper<User, User> workerWrapper2 = new WorkerWrapper.Builder<User, User>()
                .worker(work2)
                .callback(work2)
                .id("nd")
                .next(workerWrapper3)
                .param(new User("sora", 27))
                .build();
        WorkerWrapper<String, Integer> workerWrapper = new WorkerWrapper.Builder<String, Integer>()
                .worker(work1)
                .callback(work1)
                .id("st")
                .next(workerWrapper2)
                .param("test")
                .build();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Async.beginWork(10000, threadPool,workerWrapper);
        stopWatch.stop();
        logger.info("线程任务执行完成:共耗时[{}]MS", stopWatch.getLastTaskTimeMillis());
        logger.info("最终集合为[{}]", hashMap);
    }

}
