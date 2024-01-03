package com.sora.service;

import com.sora.domain.User;
import com.sora.result.Result;

/**
 * @Classname AuthService
 * @Description
 * @Date 2023/05/21 20:41
 * @Author by Sora33
 */
public interface AuthService {

    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    Result login(String name, String password);

    /**
     * 注册
     * @param user
     * @return
     */
    Result register(User user);

    /**
     * 注销
     * @return
     */
    Result logOut();
}
