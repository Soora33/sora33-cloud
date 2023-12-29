package com.sora.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sora.domain.XxlJobInfo;
import com.sora.jackson.InitObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class XxlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XxlUtil.class);

    /**
     * xxl地址
     */
    private static String xxlJobAdminAddress = "http://127.0.0.1:7777/xxl-job-admin";
    /**
     * xxl用户名
     */
    private static final String USER_NAME = "admin";
    /**
     * xxl密码
     */
    private static final String PASSWORD = "123456";
    private static final String LOGIN_URL = "/login";
    private static final String ADD_INFO_URL = "/jobinfo/add";
    private static final String REMOVE_INFO_URL = "/jobinfo/remove";
    private static final String UPDATE_INFO_URL = "/jobinfo/update";
    private static final String START_URL = "/jobinfo/start";
    private static final String STOP_URL = "/jobinfo/stop";
    private static final String PAGELIST_URL = "/jobinfo/pageList";

    private static HashMap<String, String> cookieMap = new HashMap<>();
    private static final ObjectMapper objectMapper = InitObjectMapper.initObjectMapper();

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5L, TimeUnit.SECONDS)
                .readTimeout(5L, TimeUnit.SECONDS)
                .build();

    /**
     * 登陆
     */
    private static void login() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", USER_NAME);
        paramMap.put("password", PASSWORD);

        HashMap<String, String> headerMap = new HashMap<>();
        paramMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

        Response response = postFormDataResponse(xxlJobAdminAddress + LOGIN_URL, paramMap, headerMap);

        // 检查响应状态码
        if (response.isSuccessful()) {
            // 提取Token
            String cookie = response.headers().get("Set-Cookie");
            cookieMap.put("cookie", cookie);
            response.close();
        } else {
            logger.error("登录失败，响应体：" + response);
        }
    }

    /**
     * 获取cookie
     *
     * @return
     */
    private static String getCookie() {
        String cookie = cookieMap.get("cookie");
        for (int i = 0; i < 3; i++) {
            if (cookie == null) {
                login();
                cookie = cookieMap.get("cookie");
            } else {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 添加任务
     *
     * @param xxlJobInfoMap
     * @return
     */
    public static String addJob(HashMap<String, Object> xxlJobInfoMap) {
        return link(xxlJobAdminAddress + ADD_INFO_URL, xxlJobInfoMap);
    }

    /**
     * 修改任务信息
     *
     * @param xxlJobInfoMap
     * @return
     */
    public static String updateJob(HashMap<String, Object> xxlJobInfoMap) {
        return link(xxlJobAdminAddress + UPDATE_INFO_URL, xxlJobInfoMap);
    }

    /**
     * 启动任务
     *
     * @param jobId
     * @return
     */
    public static String startJob(long jobId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", String.valueOf(jobId));
        return link(xxlJobAdminAddress + START_URL, map);
    }

    /**
     * 停止任务
     *
     * @param jobId
     * @return
     */
    public static String stopJob(long jobId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", String.valueOf(jobId));
        return link(xxlJobAdminAddress + STOP_URL, map);
    }

    /**
     * 删除
     *
     * @param jobId
     * @return
     */
    public static String removeJob(long jobId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", String.valueOf(jobId));
        return link(xxlJobAdminAddress + REMOVE_INFO_URL, map);
    }

    /**
     * 查找调度任务列表
     *
     * @param
     * @return
     */
    public static List<XxlJobInfo> pageList(long group) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("jobGroup", group);
        map.put("triggerStatus", -1);
        String response = link(xxlJobAdminAddress + PAGELIST_URL, map);
        try {
            String data = objectMapper.readTree(response).get("data").toString();
            List<XxlJobInfo> xxlJobInfos = objectMapper.readValue(data, new TypeReference<List<XxlJobInfo>>() {
            });
            return xxlJobInfos;
        } catch (JsonProcessingException e) {
            logger.error("json解析错误，[xxl-job工具类]调用pageList方法发生异常！！", e);
        }
        return new ArrayList<>();
    }

    /**
     * 与xxlJobAdmin进行交互
     * @param url
     * @param paramMap
     * @return
     */
    private static String link(String url, HashMap<String, Object> paramMap) {
        String cookie = getCookie();
        if (cookie == null) {
            return null;
        }
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookie);
        try {
            Response response = postFormDataResponse(url, paramMap, headerMap);
            String body = response.body().string();
            response.close();
            return body;
        } catch (IOException e) {
            logger.error("错误请求，请求xxlJob发生异常！", e);
        }
        return null;
    }


    /**
     * 封装响应体并请求
     * @param URL 请求路径
     * @param paramMap 参数map
     * @param headerMap 请求头map
     * @return
     */
    private static Response postFormDataResponse(String URL, HashMap<String, Object> paramMap,HashMap<String,String> headerMap) {
        // 获取headers
        Headers headers = getHeaders(headerMap);

        MultipartBody.Builder data = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        paramMap.forEach((k,v) -> {
            data.addFormDataPart(k, v.toString());
        });

        Request request = new Request.Builder()
                .post(data.build())
                .url(URL)
                .headers(headers)
                .build();

        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                return execute;
            }
        } catch (IOException e) {
            logger.error("发起请求失败！", e);
        }
        return null;
    }

    /**
     * 根据请求头map获取对应的headers
     * @param headersMap
     * @return
     */
    private static Headers getHeaders(Map<String, String> headersMap) {
        Headers.Builder builder = new Headers.Builder();
        headersMap.forEach(builder::add);
        return builder.build();
    }
}