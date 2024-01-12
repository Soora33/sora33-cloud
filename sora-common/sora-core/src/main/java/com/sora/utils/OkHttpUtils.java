package com.sora.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname OkHttpUtils
 * @Description http请求工具类
 * @Date 2023/09/05 13:26
 * @Author by Sora33
 */
public class OkHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    private static final String METHOD_GET = "get";
    private static final String METHOD_POST = "post";

    private static ObjectMapper objectMapperService = SpringUtil.getBean("ObjectMapperService");

    private static OkHttpClient okHttpClient;

    static {
        // 设置默认连接和超时时间都是5s
        OkHttpUtils.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5L, TimeUnit.SECONDS)
                .readTimeout(5L, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 发起get请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @param headersMap 请求头map
     * @return
     */
    public static String get(String URL, HashMap<String, Object> paramMap, Map<String, String> headersMap) {
        // 获取headers
        Headers headers = getHeaders(headersMap);
        // 获取参数
        String param = getParam(paramMap);
        // 拼接URL
        URL = URL + param;

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_GET);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_GET);

        return newCall(request);
    }


    /**
     * 以异步方式发起get请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @param headersMap 请求头map
     * @return
     */
    public static void getAsync(String URL, HashMap<String, Object> paramMap, Map<String, String> headersMap) {
        // 获取headers
        Headers headers = getHeaders(headersMap);
        // 获取参数
        String param = getParam(paramMap);
        // 拼接URL
        URL = URL + param;

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_GET);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_GET);

        // 发起异步调用
        newCallAsync(request);
    }


    /**
     * 发起get请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @return
     */
    public static String get(String URL, HashMap<String, Object> paramMap) {
        // 获取headers
        Headers headers = getHeaders(new HashMap<>());
        // 获取参数
        String param = getParam(paramMap);
        // 拼接URL
        URL = URL + param;

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_GET);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_GET);

        return newCall(request);
    }


    /**
     * 接口是否可以连接通
     * @param URL 请求地址
     * @return
     */
    public static boolean isSuccess(String URL) {
        // 获取headers
        Headers headers = getHeaders(new HashMap<>());

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_GET);

        try (Response execute = okHttpClient.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * 以异步方式发起get请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @return
     */
    public static void getAsync(String URL, HashMap<String, Object> paramMap) {
        // 获取headers
        Headers headers = getHeaders(new HashMap<>());
        // 获取参数
        String param = getParam(paramMap);
        // 拼接URL
        URL = URL + param;

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_GET);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_GET);

        // 发起异步调用
        newCallAsync(request);
    }


    /**
     * 发起post请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @param headersMap 请求头map
     * @return
     */
    public static String post(String URL, HashMap<String, Object> paramMap, Map<String, String> headersMap) {
        // 获取headers
        Headers headers = getHeaders(headersMap);
        // 创建请求body
        RequestBody body = getRequestBody(paramMap);

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_POST);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_POST,body);
        return newCall(request);
    }


    /**
     * 发起post请求调用（form-data）
     * @param URL 请求地址
     * @param paramMap 参数map
     * @return
     */
    public static String postFormData(String URL, HashMap<String, Object> paramMap,HashMap<String,String> headerMap) {
        // 获取headers
        Headers headers = getHeaders(headerMap);

        MultipartBody.Builder data = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        paramMap.forEach((k,v) -> {
            data.addFormDataPart(k, v.toString());
        });

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_POST);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_POST,data.build());
        return newCall(request);
    }

    /**
     * 以异步方式发起post请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @param headersMap 请求头map
     * @return
     */
    public static void postAsync(String URL, HashMap<String, Object> paramMap, Map<String, String> headersMap) {
        // 获取headers
        Headers headers = getHeaders(headersMap);
        // 创建请求body
        RequestBody body = getRequestBody(paramMap);

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_POST);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_POST,body);

        // 发起异步调用
        newCallAsync(request);
    }


    /**
     * 发起post请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @return
     */
    public static String post(String URL, HashMap<String, Object> paramMap) {
        // 获取headers
        Headers headers = getHeaders(new HashMap<>());
        // 创建请求body
        RequestBody body = getRequestBody(paramMap);
        
        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_POST);
        
        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_POST,body);
        return newCall(request);
    }


    /**
     * 以异步方式发起post请求调用
     * @param URL 请求地址
     * @param paramMap 参数map
     * @return
     */
    public static void postAsync(String URL, HashMap<String, Object> paramMap) {
        // 获取headers
        Headers headers = getHeaders(new HashMap<>());
        // 创建请求body
        RequestBody body = getRequestBody(paramMap);

        logger.info("本次访问请求地址：[{}]，请求方式：[{}]，正在发起请求……", URL, OkHttpUtils.METHOD_POST);

        Request request = getRequest(URL, headers, OkHttpUtils.METHOD_POST,body);

        // 发起异步调用
        newCall(request);
    }


    /**
     * 获取requestBody对象
     * @param paramMap
     * @return
     */
    private static RequestBody getRequestBody(HashMap<String, Object> paramMap) {
        try {
            String value = objectMapperService.writeValueAsString(paramMap);
            return RequestBody.create(MediaType.parse("application/json"), value);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错！", e);
        }
        return RequestBody.create(MediaType.parse("application/json"),"{}");
    }


    /**
     * 获取request请求
     * @param URL
     * @param headers
     * @param method
     * @param requestBody
     * @return
     */
    private static Request getRequest(String URL, Headers headers, String method, RequestBody... requestBody) {
        if (requestBody.length > 1) {
            throw new RuntimeException("requestBody最多只允许一个！");
        }
        if (OkHttpUtils.METHOD_GET.equalsIgnoreCase(method)) {
            return new Request.Builder()
                    .get()
                    .url(URL)
                    .headers(headers)
                    .build();
        } else if (OkHttpUtils.METHOD_POST.equalsIgnoreCase(method)) {
            return new Request.Builder()
                    .post(requestBody[0])
                    .url(URL)
                    .headers(headers)
                    .build();
        }
        return new Request.Builder().build();
    }


    /**
     * 发起请求
     * @param request
     * @return
     */
    private static String newCall(Request request) {
        try(Response execute = okHttpClient.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                ResponseBody body = execute.body();
                String result = body == null ? null : body.string();
//                logger.info("接口请求访问成功，返回值：[{}]", result);
                return result;
            }
            logger.error("接口请求失败！错误码：[{}]", execute.code());
        } catch (IOException e) {
            logger.error("接口请求失败！失败原因：", e);
        }
        return null;
    }


    /**
     * 发起异步请求
     * @param request
     * @return
     */
    private static void newCallAsync(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error("接口请求失败！", e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    String result = body == null ? null : body.string();
                    logger.info("接口请求访问成功，返回值：[{}]", result);
                    return;
                }
                logger.error("接口请求失败！错误码：[{}]", response.code());
            }
        });
    }


    /**
     * map转为get参数
     * @param paramMap
     * @return
     */
    private static String getParam(Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        paramMap.forEach((key, value) -> {
            sb.append("&").append(key).append("=").append(value);
        });
        return sb.toString().replaceFirst("&", "?");
    }


    /**
     * 创建并装载请求头
     *
     * @param headersMap
     * @return
     */
    private static Headers getHeaders(Map<String, String> headersMap) {
        Headers.Builder builder = new Headers.Builder();
        headersMap.forEach(builder::add);
        return builder.build();
    }
}
