package com.sora.logic;

import com.google.common.collect.Lists;
import com.sora.config.SoraExecutorPool;
import com.sora.constant.PictureConstant;
import com.sora.enums.PictureUrlEnums;
import com.sora.util.SpiderUtils;
import com.sora.utils.OkHttpUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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


    public List<String> getWallHavenData(String param, int pageNum) {
        PictureUrlEnums wallhaven = PictureUrlEnums.WALLHAVEN;
        String url = wallhaven.getUrl();
        if (pageNum != 1) {
            param += "&page=" + pageNum;
        }
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
        // 固定模板
        String pre = "https://w.wallhaven.cc/full/youmiyasorahinanaa/wallhaven-inorilovesssoo.jpg";
        ArrayList<String> list = Lists.newArrayList();
        // 修改url
        for (String imgUrl : hrefList) {
            // 获取图片定位部分
            String hash = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
            // 获取定位前2位
            String hashFirstTwoChar = hash.substring(0, 2);
            list.add(pre.replace("youmiyasorahinanaa", hashFirstTwoChar).replace("inorilovesssoo", hash));
        }
        return list;
    }

    public int getWallHavenCount(String param) {
        PictureUrlEnums wallhaven = PictureUrlEnums.WALLHAVEN;
        String url = wallhaven.getUrl();
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search?q=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);
        Elements h1 = doc.getElementsByTag("h1");
        String count = h1.text().substring(0, h1.text().indexOf("Wall") - 1);
        return Integer.parseInt(count.replace(",", ""));
    }


    public List<String> getHippopxData(String param, int pageNum) {

        PictureUrlEnums wallhaven = PictureUrlEnums.HIPPOPX;
        String url = wallhaven.getUrl();

        if (pageNum != 1) {
            param += "&page=" + pageNum;
        }
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

    public int getHippopxCount(String param) {
        PictureUrlEnums wallhaven = PictureUrlEnums.HIPPOPX;
        String url = wallhaven.getUrl();
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search?q=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);
        String text = doc.select("[class=search_h1]").text();
        try {
            return Integer.parseInt(text.substring(text.indexOf("图片数量:") + 6));
        } catch (Exception e) {
            return PictureConstant.PAGE_SIZE;
        }
    }


    public List<String> getPaperData(String param, int pageNum) {
        PictureUrlEnums wallpaperscraft = PictureUrlEnums.WALLPAPERSCRAFT;
        String url = wallpaperscraft.getUrl();

        if (pageNum != 1) {
            param += "&page=" + pageNum;
        }
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search/?query=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);

        List<String> imageList = doc.select("[class=wallpapers__image]").stream()
                .map(data -> data.attr("src"))
                .map(data -> data.replace("300x168", "1920x1080"))
                .toList();

        // 校验图片url有效性
        List<CompletableFuture<String>> completableFutureList = imageList.stream()
                .map(data -> CompletableFuture.supplyAsync(() -> this.replaceImageUrlSize(data), soraThreadPool))
                .toList();

        return completableFutureList.stream().map(CompletableFuture::join).toList();
    }

    private String replaceImageUrlSize(String imgUrl) {
        return OkHttpUtils.isSuccess(imgUrl) ? imgUrl : imgUrl.replace("1920x1080", "1280x720");
    }


    public int getPaperCount(String param) {
        PictureUrlEnums wallpaperscraft = PictureUrlEnums.WALLPAPERSCRAFT;
        String url = wallpaperscraft.getUrl();
        // 获取连接
        Connection.Response connect = SpiderUtils.getConnect(url, "search/?query=", param);
        String body = connect.body();
        // 获得所有元素
        Document doc = Jsoup.parse(body);
        try {
            String pageIndex = doc.select("[class=pager__link]").get(3).attr("href");
            String pageStr = pageIndex.substring(pageIndex.indexOf("page="), pageIndex.indexOf("&query="));
            return Integer.parseInt(pageStr.split("=")[1]) * PictureConstant.PAGE_SIZE;
        } catch (Exception e) {
            return PictureConstant.PAGE_SIZE;
        }
    }
}
