package com.sora.service;

import com.sora.result.Result;

/**
 * @Classname PictureService
 * @Description
 * @Date 2023/11/23 16:30
 * @Author by Sora33
 */
public interface PictureService {

    /**
     * 根据参数返回对应图片集合
     *
     * @param pageNum
     * @param param
     * @return
     */
    Result select(int pageNum, String param);
}
