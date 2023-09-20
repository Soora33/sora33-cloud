package com.sora.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname RabbitListen
 * @Description
 * @Date 2023/09/11 16:52
 * @Author by Sora33
 */
@Component
public class RabbitListen {

    private static final Logger logger = LoggerFactory.getLogger(RabbitListen.class);

    @RabbitListener(queues = {"test"})
    public void test(String msg) {
        logger.info("test队列接收到消息：[{}]", msg);
    }


    @RabbitListener(queues = {"testDir"})
    public void testDExchange(String msg) {
        logger.info("testDir队列接收到消息：[{}]", msg);
    }


    @RabbitListener(queues = {"testFan"})
    public void testFExchange(String msg) {
        logger.info("testFan队列接收到消息：[{}]",msg);
    }


    @RabbitListener(queues = {"sora33"})
    public void sora33FExchange(String msg) {
        logger.info("sora33队列接收到消息：[{}]",msg);
    }
}
