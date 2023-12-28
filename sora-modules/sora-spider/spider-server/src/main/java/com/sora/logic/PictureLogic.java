package com.sora.logic;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.sora.config.SoraExecutorPool;
import com.sora.constant.LogConstants;
import com.sora.enums.PictureUrlEnums;
import com.sora.util.SpiderUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Classname PictureLogic
 * @Description 爬虫逻辑类
 * @Date 2023/12/14 10:34
 * @Author by Sora33
 */
@Component
public class PictureLogic {

    private static final Logger logger = LoggerFactory.getLogger(PictureLogic.class);

    private final ThreadPoolExecutor soraThreadPool;

    public PictureLogic(ThreadPoolExecutor soraThreadPool) {
        this.soraThreadPool = SoraExecutorPool.getThreadPoolExecutor();
    }


    public List<String> getWallHavenData(String param) {
        PictureUrlEnums wallhaven = PictureUrlEnums.WALLHAVEN;
        String url = wallhaven.getUrl();
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search?q=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);
        // 获取图片所在class和href至集合
        Elements elements = doc.select("[class='preview']");
        List<String> hrefList = elements.stream()
                .map(data -> data.attr("href"))
                .toList();

        // 将href地址拆分为12个集合 准备12个线程异步读取
        List<List<String>> partition = Lists.partition(hrefList, 1).subList(0,12);

        // 创建异步任务集合
        List<CompletableFuture<List<String>>> completableFutureList = partition.stream()
                .map(data -> CompletableFuture.supplyAsync(() -> this.readUrlPicPath(data), soraThreadPool))
                .toList();

        // 获取异步任务结果并归类为集合
        List<String> pictureUrlList = completableFutureList.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        logger.info("初始图片个数：{}",pictureUrlList.size());
        pictureUrlList = pictureUrlList.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
        logger.info("图片有值个数：{}",pictureUrlList.size());
        return pictureUrlList;
    }


    public List<String> getHippopxData(String param) {

        PictureUrlEnums wallhaven = PictureUrlEnums.HIPPOPX;
        String url = wallhaven.getUrl();

        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search?q=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);

        List<String> imgList = doc.select("img")
                .stream()
                .filter(data -> data.toString().contains("contentUrl"))
                .map(data -> data.attr("src"))
                .collect(Collectors.toList());

        return imgList;
    }


    public List<String> readUrlPicPath(List<String> hrefList) {
        return hrefList.stream().map(data -> {
            try {
                Connection.Response hrefConnect = SpiderUtils.getConnect(data, null, null);
                String wallBody = hrefConnect.body();
                Document bodyDoc = Jsoup.parse(wallBody);
                return bodyDoc.select("[id='wallpaper']").attr("src");
            } catch (Exception e) {
                logger.error("{}，读取图片详情页发生错误！", LogConstants.ERROR_LOG);
                return null;
            }
        }).collect(Collectors.toList());
    }
}
