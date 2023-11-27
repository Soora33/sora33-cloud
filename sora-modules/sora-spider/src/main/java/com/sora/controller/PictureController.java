package com.sora.controller;

import com.sora.result.Result;
import com.sora.service.PictureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname PictureController
 * @Description 图片Spider
 * @Date 2023/11/23 16:28
 * @Author by Sora33
 */
@RequestMapping("/picture")
@RestController
public class PictureController {


    public final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * 根据参数返回对应图片集合
     * @param param
     * @return
     */
    @GetMapping("select/{param}")
    private Result select(@PathVariable("param") String param) {
        return pictureService.select(param);
    }
}
