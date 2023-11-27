package com.sora.util;

import com.sora.constant.LogConstants;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
            return Jsoup.
                    connect(url + placeholder + param)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
        } catch (IOException e) {
            logger.error("{}，建立jsoup连接失败！", LogConstants.ERROR_LOG, e);
        }
        return null;
    }
}
