package com.sora.xxljob;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sora.constant.LogConstants;
import com.sora.jackson.InitObjectMapper;
import com.sora.result.Result;
import com.sora.utils.ServiceLink;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname XxlExecutor
 * @Description xxl公用执行者
 * @Date 2023/11/30 16:13
 * @Author by Sora33
 */
@Component
public class XxlExecutor {

    private final Logger logger = LoggerFactory.getLogger(XxlExecutor.class);

    @Resource
    private ObjectMapper objectMapper = InitObjectMapper.initObjectMapper();

    @Resource
    private ServiceLink serviceLink;

    @XxlJob("soraXxl")
    private void xxlHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        try {
            JsonNode jsonNode = objectMapper.readTree(jobParam);
            String server = jsonNode.get("server").asText();
            String requestType = jsonNode.get("requestType").asText();
            String path = jsonNode.get("path").asText();

            String response;
            if ("GET".equalsIgnoreCase(requestType)) {
                response = serviceLink.serviceLink(server, path, true);
            } else {
                response = serviceLink.serviceLink(server, path, false);
            }
            Result result = objectMapper.readValue(response, Result.class);
            // 设置返回值
            XxlJobHelper.handleSuccess(result.toString());
        } catch (JsonProcessingException e) {
            logger.error("{}，xxl执行任务出错！", LogConstants.ERROR_LOG);
            throw new RuntimeException(e);
        }
    }

}
