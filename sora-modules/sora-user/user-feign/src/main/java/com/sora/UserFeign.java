package com.sora;

import com.sora.domain.User;
import com.sora.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname UserFeign
 * @Description 用户远程调用
 * @Date 2023/11/22 10:54
 * @Author by Sora33
 */
@FeignClient(name = "sora-user")
public interface UserFeign {

    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/user/select/{name}/{password}")
    Result<User> login(@PathVariable("name") String name, @PathVariable("password") String password);

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/user/insert")
    Result register(@RequestBody User user);
}
