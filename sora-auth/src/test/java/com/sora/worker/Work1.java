package com.sora.worker;

import cn.hutool.core.util.RandomUtil;
import com.sora.utils.async.callback.ICallback;
import com.sora.utils.async.callback.IWorker;
import com.sora.utils.async.worker.WorkResult;
import com.sora.utils.async.wrapper.WorkerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Classname Work1
 * @Description
 * @Date 2023/06/03 08:59
 * @Author by Sora33
 */
public class Work1 implements IWorker<String, Integer>, ICallback<String, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Work1.class);

    @Override
    public Integer action(String object, Map<String, WorkerWrapper> allWrappers) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("【默认线程】开始线程任务，方法入参[{}]", object);
        return RandomUtil.randomInt(1,3);
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }


    @Override
    public void begin() {
        logger.info("【默认线程】线程开始执行，线程名称：[{}]", Thread.currentThread().getName());
    }

    @Override
    public void result(boolean success, String param, WorkResult<Integer> workResult) {
        if (success) {
            logger.info("【默认线程】线程执行完成！");
        } else {
            logger.error("【默认线程】线程执行失败！");
        }
    }

}
