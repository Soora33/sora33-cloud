package com.sora.service;

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
     * @param userId
     * @param password
     * @return
     */
    Result selectUserById(String userId, String password);
}
