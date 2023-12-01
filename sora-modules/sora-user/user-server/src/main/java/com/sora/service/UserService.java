package com.sora.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sora.domain.User;
import com.sora.result.Result;

/**
 * @Classname UserService
 * @Description
 * @Date 2023/11/22 10:24
 * @Author by Sora33
 */
public interface UserService extends IService<User> {

    /**
     * 查询所有用户
     * @return
     */
    Result select();

    /**
     * 新增用户
     * @param user
     * @return
     */
    Result insert(User user);

    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    Result login(String name, String password);
}
