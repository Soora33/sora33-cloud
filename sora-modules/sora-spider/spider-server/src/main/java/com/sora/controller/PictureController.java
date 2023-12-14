package com.sora.controller;

import com.sora.anno.UserLogAnno;
import com.sora.constants.UserLogConstants;
import com.sora.result.Result;
import com.sora.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RestController
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * 根据参数返回对应图片集合
     * @param param
     * @return
     */
    @UserLogAnno(type = UserLogConstants.SELECT, description = "爬取图片")
    @Operation(summary = "爬取图片", description = "根据参数爬取对应图片")
    @GetMapping("select/{param}")
    public Result select(@Parameter(description = "图片英文名") @PathVariable("param") String param) {
        return pictureService.select(param);
    }
}

