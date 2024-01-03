package com.sora.util;

import com.sora.constant.LogConstants;
import com.sora.constant.SpiderConstant;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname SpiderUtils
 * @Description spider通用方法
 * @Date 2023/11/24 09:10
 * @Author by Sora33
 */
public class SpiderUtils {
    private static final Logger logger = LoggerFactory.getLogger(SpiderUtils.class);

    /**
     * 占位符
     * @param url
     * @param param
     * @return
     */
    public static Connection.Response getConnect(String url,String placeholder,String param) {
        try {
            placeholder = placeholder == null ? "" : placeholder;
            param = param == null ? "" : param;
            return Jsoup.
                    connect(url + placeholder + param)
                    .userAgent(SpiderConstant.USER_AGENT.get(new Random().nextInt(SpiderConstant.USER_AGENT.size())))
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute();
        } catch (Exception e) {
            logger.error("{}，建立jsoup连接失败！", LogConstants.ERROR_LOG, e);

        }
        return null;
    }

    /**
     * 中文校验
     * @param input
     * @return
     */
    public static boolean isValidInput(String input) {
        // 不能包含英文和数字的正则表达式
        String regex = "^[a-zA-Z0-9]+$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(input);

        // 使用 find 方法进行匹配
        return !matcher.find();
    }
}
