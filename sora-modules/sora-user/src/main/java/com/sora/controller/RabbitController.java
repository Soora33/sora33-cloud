package com.sora.controller;

import com.sora.result.Result;
import com.sora.utils.RabbitMqUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Classname RabbitController
 * @Description rabbit控制器
 * @Date 2023/09/11 15:55
 * @Author by Sora33
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {


    private final RabbitMqUtils rabbitMqUtils;

    private static final Logger logger = LoggerFactory.getLogger(RabbitController.class);

    public RabbitController(RabbitMqUtils rabbitMqUtils) {
        this.rabbitMqUtils = rabbitMqUtils;
    }


    @PostMapping("send")
    public Result sendMsg(@RequestBody HashMap<String, String> param) {
        rabbitMqUtils.sendMsgToExchange(param.get("msg"), param.get("queue"));
        return Result.success();
    }

    @PostMapping("sendFanout")
    public Result sendMsgToFanout(@RequestBody HashMap<String, String> param) {
        rabbitMqUtils.sendMsgToFanoutFanoutExchange(param.get("msg"), param.get("queue"));
        return Result.success();
    }

    @PostMapping("sendDirect")
    public Result sendMsgToDirect(@RequestBody HashMap<String, String> param) {
        rabbitMqUtils.sendMsgToFanoutDirectExchange(param.get("msg"), param.get("queue"), param.get("key"));
        return Result.success();
    }

    @GetMapping("add/queue/{name}")
    public Result createQueue(@PathVariable("name") String name) {
        rabbitMqUtils.createQueue(name, true);
        return Result.success();
    }

    @GetMapping("add/Fexchange/{name}")
    public Result createFanoutExchange(@PathVariable("name") String name) {
        rabbitMqUtils.createFanoutExchange(name, true, false);;
        return Result.success();
    }


    @GetMapping("add/Dexchange/{name}")
    public Result createDirectExchange(@PathVariable("name") String name) {
        rabbitMqUtils.createDirectExchange(name, true, false);
        return Result.success();

    }


    @GetMapping("buildF/{queueName}/{exchangeName}")
    public Result buildFanout(@PathVariable("queueName") String queueName, @PathVariable("exchangeName") String exchangeName) {
        rabbitMqUtils.buildQueueToFanoutExchange(queueName, exchangeName);
        return Result.success();
    }


    @GetMapping("buildD/{queueName}/{exchangeName}/{key}")
    public Result buildDirect(@PathVariable("queueName") String queueName, @PathVariable("exchangeName") String exchangeName,
                              @PathVariable("key") String key) {
        rabbitMqUtils.buildQueueToDirectExchange(queueName, exchangeName,key);
        return Result.success();
    }


    @GetMapping("unBuildF/{queueName}/{exchangeName}")
    public Result unBuildFanout(@PathVariable("queueName") String queueName, @PathVariable("exchangeName") String exchangeName) {
        rabbitMqUtils.unBuildWithFanoutExchange(queueName, exchangeName);
        return Result.success();
    }


    @GetMapping("unBuildD/{queueName}/{exchangeName}/{key}")
    public Result unBuildDirect(@PathVariable("queueName") String queueName, @PathVariable("exchangeName") String exchangeName,
                              @PathVariable("key") String key) {
        rabbitMqUtils.unBuildWithDirectExchange(queueName, exchangeName,key);
        return Result.success();
    }


    @DeleteMapping("queue/{name}")
    public Result deleteQueue(@PathVariable("name") String name) {
        rabbitMqUtils.deleteQueue(name);
        return Result.success();
    }


    @DeleteMapping("exchange/{name}")
    public Result deleteExChange(@PathVariable("name") String name) {
        rabbitMqUtils.deleteExchange(name);
        return Result.success();
    }

}
