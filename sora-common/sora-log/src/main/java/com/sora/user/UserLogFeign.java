package com.sora.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname UserLogFeign
 * @Description 用户服务远程调用
 * @Date 2023/11/28 13:22
 * @Author by Sora33
 */
@FeignClient(name = "sora-system")
public interface UserLogFeign {

    @PostMapping("/userLog/insert")
    void insert(@RequestBody UserLog userLog);
}
