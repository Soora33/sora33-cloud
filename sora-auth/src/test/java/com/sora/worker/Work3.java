package com.sora.worker;

import com.sora.domain.User;
import com.sora.utils.async.callback.ICallback;
import com.sora.utils.async.callback.IWorker;
import com.sora.utils.async.worker.WorkResult;
import com.sora.utils.async.wrapper.WorkerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Work3
 * @Description
 * @Date 2023/06/03 08:59
 * @Author by Sora33
 */
public class Work3 implements IWorker<HashMap<String, Object>, HashMap<String, Object>>, ICallback<HashMap<String, Object>, HashMap<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(Work3.class);


    @Override
    public void begin() {
        logger.info("【最终汇总线程】正在将用户对象加入到map");
    }

    @Override
    public void result(boolean success, HashMap<String, Object> param, WorkResult<HashMap<String, Object>> workResult) {
        if (success) {
            logger.info("【最终汇总线程】线程任务执行完成！");
        } else {
            logger.error("【最终汇总线程】线程执行失败！输入对象[{}]", param);
        }
    }

    @Override
    public HashMap<String, Object> action(HashMap<String, Object> object, Map<String, WorkerWrapper> allWrappers) {
        // 获取第二个异步任务的结果
        WorkerWrapper ndWorker = allWrappers.get("nd");
        // 对结果进行判断
        if (ndWorker != null && ndWorker.getWorkResult().getResult() != null) {
            User user = (User)ndWorker.getWorkResult().getResult();
            logger.info("【最终汇总线程】第二个异步任务返回用户名称为[{}]，正在对用户加入到集合", user.getName());
            object.put(user.getName(), user);
            return object;
        } else {
            logger.warn("【最终汇总线程】第二个异步任务返回值为null，终止汇总");
        }
        return object;
    }

    @Override
    public HashMap<String, Object> defaultValue() {
        return new HashMap<>();
    }
}
