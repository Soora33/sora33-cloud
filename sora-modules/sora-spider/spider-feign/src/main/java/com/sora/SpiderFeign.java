package com.sora;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Classname SpiderFeign
 * @Description spider对外服务
 * @Date 2023/12/01 13:46
 * @Author by Sora33
 */
@FeignClient(name = "sora-spider")
public interface SpiderFeign {
}
