package com.sora.service.impl;

import com.sora.constant.PictureConstant;
import com.sora.logic.PictureLogic;
import com.sora.result.Result;
import com.sora.service.PictureService;
import com.sora.util.SpiderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Classname PictureServiceImpl
 * @Description
 * @Date 2023/11/23 16:30
 * @Author by Sora33
 */
@Service
public class PictureServiceImpl implements PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    private final PictureLogic pictureLogic;

    public PictureServiceImpl(PictureLogic pictureLogic) {
        this.pictureLogic = pictureLogic;
    }


    /**
     * 根据参数返回对应图片集合
     *
     * @param pageNum
     * @param param
     * @return
     */
    @Override
    public Result select(int pageNum, String param) {
        if (!SpiderUtils.isValidInput(param)) {
            return Result.error("仅支持英文查找！");
        }
        List<String> paperData = pictureLogic.getPaperData(param, pageNum);
        int paperCount = pictureLogic.getPaperCount(param);
        // 默认展示个数
        paperData = paperData.subList(0, Math.min(paperData.size(), PictureConstant.PAGE_SIZE));
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", paperData);
        map.put("count", paperCount);
        return Result.success(map);
    }
}
