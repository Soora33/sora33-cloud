package com.sora.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.sora.config.SoraExecutorPool;
import com.sora.constant.PictureConstant;
import com.sora.result.Result;
import com.sora.service.PictureService;
import com.sora.util.SpiderUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Classname PictureServiceImpl
 * @Description
 * @Date 2023/11/23 16:30
 * @Author by Sora33
 */
@Service
public class PictureServiceImpl implements PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    private final ThreadPoolExecutor soraThreadPool;

    public PictureServiceImpl(ThreadPoolExecutor threadPoolExecutor) {
        this.soraThreadPool = SoraExecutorPool.getThreadPoolExecutor();
    }

    /**
     * 根据参数返回对应图片集合
     * @param param
     * @return
     */
    @Override
    public Result select(String param) {
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(PictureConstant.PICTURE_URL, "search?q=", param);
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
                .map(data -> CompletableFuture.supplyAsync(() -> {
                    return this.readUrlPicPath(data);
        }, soraThreadPool)).toList();

        // 获取异步任务结果并归类为集合
        List<String> pictureUrlList = completableFutureList.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println(pictureUrlList.size());
        pictureUrlList = pictureUrlList.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
        System.out.println(pictureUrlList.size());
        return Result.success(pictureUrlList);
    }


    public List<String> readUrlPicPath(List<String> hrefList) {
        return hrefList.stream().map(data -> {
            try {
                Connection.Response hrefConnect = SpiderUtils.getConnect(data, "", "");
                String wallBody = hrefConnect.body();
                Document bodyDoc = Jsoup.parse(wallBody);
                return bodyDoc.select("[id='wallpaper']").attr("src");
            } catch (Exception e) {
                return null;
            }
        }).collect(Collectors.toList());
    }
}
