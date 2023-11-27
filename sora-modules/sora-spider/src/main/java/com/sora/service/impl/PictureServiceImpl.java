package com.sora.service.impl;

import com.sora.constant.PictureConstant;
import com.sora.result.Result;
import com.sora.service.PictureService;
import com.sora.util.SpiderUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname PictureServiceImpl
 * @Description
 * @Date 2023/11/23 16:30
 * @Author by Sora33
 */
@Service
public class PictureServiceImpl implements PictureService {

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
        // 获取图片所在class
        Elements elements = doc.select("[class='preview']");
        List<String> hrefList = elements.stream()
                .map(data -> data.attr("href"))
                .toList();
        // 收集所有图片地址
        List<String> hrefUrlList = hrefList.stream().map(data -> {
            Connection.Response hrefConnect = SpiderUtils.getConnect(data, "", "");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String wallBody = hrefConnect.body();
            Document bodyDoc = Jsoup.parse(wallBody);
            return bodyDoc.select("[id='wallpaper']").attr("src");
        }).collect(Collectors.toList());

        return Result.success(hrefUrlList);
    }
}
