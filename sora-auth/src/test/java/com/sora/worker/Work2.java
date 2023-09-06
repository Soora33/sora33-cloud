package com.sora.worker;

import com.sora.domain.User;
import com.sora.utils.async.callback.ICallback;
import com.sora.utils.async.callback.IWorker;
import com.sora.utils.async.worker.WorkResult;
import com.sora.utils.async.wrapper.WorkerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Classname Work2
 * @Description
 * @Date 2023/06/03 08:59
 * @Author by Sora33
 */
public class Work2 implements IWorker<User, User>, ICallback<User, User> {

    private static final Logger logger = LoggerFactory.getLogger(Work2.class);


    @Override
    public void begin() {
        logger.info("【用户年龄转换线程】正在对用户年龄+1");
    }

    @Override
    public void result(boolean success, User param, WorkResult<User> workResult) {
        if (success) {
            logger.info("【用户年龄转换线程】线程任务执行完成！");
        } else {
            logger.error("【用户年龄转换线程】线程执行失败！输入用户[{}]", param);
        }
    }

    @Override
    public User action(User object, Map<String, WorkerWrapper> allWrappers) {
        // 获取第一个异步任务的结果
        WorkerWrapper stWorker = allWrappers.get("st");
        // 对结果进行判断
        if (stWorker != null && (Integer) stWorker.getWorkResult().getResult() % 2 == 0) {
            logger.info("【用户年龄转换线程】第一个异步任务返回值为[{}]，正在对用户年龄进行转换", stWorker.getWorkResult().getResult());
            object.setAge(object.getAge() + 1);
            return object;
        } else {
            logger.warn("第一个异步任务结果返回值为[{}]，不符合年龄转换条件，终止转换", stWorker.getWorkResult().getResult());
        }
        return null;
    }

    @Override
    public User defaultValue() {
        logger.error("【用户年龄转换线程】转换年龄出错！已返回默认用户对象");
        return new User("default",0);
    }
}
