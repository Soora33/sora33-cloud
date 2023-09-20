package com.sora.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname RabbitMqUtils
 * @Description rabbitMq常用方法
 * @Date 2023/09/11 13:57
 * @Author by Sora33
 */
@Component
public class RabbitMqUtils {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqUtils.class);

    private final String EXCHANGE = "normal";
    private final String DIRECT_EXCHANGE = "direct";
    private final String FANOUT_EXCHANGE = "fanout";

    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    public RabbitMqUtils(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (!b) {
                logger.error("消息发送到交换机失败，失败详情：[{}]", s);
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            logger.error("交换机根据键路由到队列失败，消息详情：[{}]", returnedMessage);
            // 直接丢弃失败消息
            rabbitTemplate.setMandatory(true);
        });
        this.rabbitTemplate = rabbitTemplate;
        rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        // 设置为true，让Spring加载rabbitAdmin
        rabbitAdmin.setAutoStartup(true);
    }


    /**
     * 创建队列并加入rabbitMq
     *
     * @param queueName 队列名
     * @param isEnd     是否持久化
     * @return
     */
    public boolean createQueue(String queueName, boolean isEnd) {
        try {
            rabbitAdmin.declareQueue(new Queue(queueName, isEnd));
            logger.info("队列[{}]创建成功", queueName);
            return true;
        } catch (Exception e) {
            logger.error("创建队列：[{}]失败：", queueName, e);
            return false;
        }
    }


    /**
     * 删除队列
     *
     * @param queueName 队列名
     * @return
     */
    public boolean deleteQueue(String queueName) {
        try {
            rabbitAdmin.deleteQueue(queueName);
            logger.info("删除队列[{}]成功", queueName);
            return true;
        } catch (Exception e) {
            logger.error("删除队列[{}]失败！", queueName, e);
            return false;
        }
    }


    /**
     * 删除交换机
     *
     * @param exchangeName 队列名
     * @return
     */
    public boolean deleteExchange(String exchangeName) {
        try {
            rabbitAdmin.deleteExchange(exchangeName);
            logger.info("删除交换机[{}]成功", exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("删除交换机[{}]失败！", exchangeName, e);
            return false;
        }
    }


    /**
     * 创建fanout交换机并加入rabbitMq
     * <p color='red'>广播交换机，用于一对多，将消息转发到所有绑定了该交换机的队列</p>
     *
     * @param exchangeName 交换机名
     * @param durable      是否持久化
     * @param autoDelete   该交换机没有绑定队列是否自动删除，一般为false
     * @return
     */
    public boolean createFanoutExchange(String exchangeName, boolean durable, boolean autoDelete) {
        try {
            rabbitAdmin.declareExchange(new FanoutExchange(exchangeName, durable, autoDelete));
            logger.info("Fanout交换机：[{}]创建成功", exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("Fanout交换机：[{}]创建失败：", exchangeName, e);
            return false;
        }
    }


    /**
     * 创建direct交换机并加入rabbitMq
     * <p color='red'>路由交换机，用于一对一，将消息根据路由key转发到对应队列</p>
     *
     * @param exchangeName 交换机名
     * @param durable      是否持久化
     * @param autoDelete   该交换机没有绑定队列是否自动删除，一般为false
     * @return
     */
    public boolean createDirectExchange(String exchangeName, boolean durable, boolean autoDelete) {
        try {
            rabbitAdmin.declareExchange(new DirectExchange(exchangeName, durable, autoDelete));
            logger.info("Direct交换机：[{}]创建成功", exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("Direct交换机：[{}]创建失败：", exchangeName, e);
            return false;
        }
    }


    /**
     * 绑定队列到fanout交换机
     *
     * @param queueName    队列名
     * @param exchangeName 交换机名
     * @return
     */
    public boolean buildQueueToFanoutExchange(String queueName, String exchangeName) {
        try {
            Binding binding = BindingBuilder.bind(new Queue(queueName)).to(new FanoutExchange(exchangeName));
            rabbitAdmin.declareBinding(binding);
            logger.info("绑定队列[{}]到Fanout交换机[{}]成功", queueName, exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("绑定队列[{}]到Fanout交换机[{}]失败", queueName, exchangeName, e);
            return false;
        }
    }


    /**
     * 绑定队列到direct交换机
     *
     * @param queueName    队列名
     * @param exchangeName 交换机名
     * @param key          路由键
     * @return
     */
    public boolean buildQueueToDirectExchange(String queueName, String exchangeName, String key) {
        try {
            Binding binding = BindingBuilder.bind(new Queue(queueName)).to(new DirectExchange(exchangeName)).with(key);
            rabbitAdmin.declareBinding(binding);
            logger.info("绑定队列[{}]到Direct交换机[{}]成功", queueName, exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("绑定队列[{}]到Direct交换机[{}]成功", queueName, exchangeName, e);
            return false;
        }
    }


    /**
     * 解除绑定队列到Fanout交换机
     *
     * @param queueName    队列名
     * @param exchangeName 交换机名
     * @return
     */
    public boolean unBuildWithFanoutExchange(String queueName, String exchangeName) {
        try {
            Binding binding = BindingBuilder.bind(new Queue(queueName)).to(new FanoutExchange(exchangeName));
            rabbitAdmin.removeBinding(binding);
            logger.info("解绑队列[{}]到Fanout交换机[{}]成功", queueName, exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("解绑队列[{}]到Fanout交换机[{}]成功", queueName, exchangeName, e);
            return false;
        }
    }


    /**
     * 解除绑定队列到Direct交换机
     *
     * @param queueName    队列名
     * @param exchangeName 交换机名
     * @param key          路由键
     * @return
     */
    public boolean unBuildWithDirectExchange(String queueName, String exchangeName, String key) {
        try {
            Binding binding = BindingBuilder.bind(new Queue(queueName)).to(new DirectExchange(exchangeName)).with(key);
            rabbitAdmin.removeBinding(binding);
            logger.info("解绑队列[{}]到Direct交换机[{}]成功", queueName, exchangeName);
            return true;
        } catch (Exception e) {
            logger.error("解绑队列[{}]到Direct交换机[{}]成功", queueName, exchangeName, e);
            return false;
        }
    }


    /**
     * 发送消息到指定交换机
     *
     * @return
     */
    public boolean sendMsgToExchange(String message, String queue) {
        return this.convertAndSend(message, queue, null, EXCHANGE);
    }


    /**
     * 发送消息到指定Fanout交换机
     *
     * @return
     */
    public boolean sendMsgToFanoutFanoutExchange(String message, String exchange) {
        return this.convertAndSend(message, exchange, null, FANOUT_EXCHANGE);
    }


    /**
     * 发送消息到指定Direct交换机
     *
     * @return
     */
    public boolean sendMsgToFanoutDirectExchange(String message, String exchange, String key) {
        return this.convertAndSend(message, exchange, key, DIRECT_EXCHANGE);
    }


    /**
     * 发送消息到交换机，如发送失败控制台会打印错误日志
     * @param msg
     * @param queueORExchange
     * @param key
     * @param type
     * @return
     */
    private boolean convertAndSend(String msg, String queueORExchange, String key, String type) {
        try {
            switch (type) {
                case EXCHANGE -> rabbitTemplate.convertAndSend(queueORExchange, msg);
                case FANOUT_EXCHANGE -> rabbitTemplate.convertAndSend(queueORExchange, "", msg);
                case DIRECT_EXCHANGE -> rabbitTemplate.convertAndSend(queueORExchange, key, msg);
                default -> {
                    logger.error("错误的交换机类型！！！");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("消息：[{}]发送到队列或交换机：[{}]错误!",msg, queueORExchange);
            return false;
        }
    }
}
