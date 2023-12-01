package com.sora;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Classname SystemFeign
 * @Description 系统对外服务
 * @Date 2023/12/01 13:43
 * @Author by Sora33
 */
@FeignClient(name = "sora-system")
public interface SystemFeign {
}
