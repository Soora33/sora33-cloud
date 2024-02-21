package com.sora.service;

import com.sora.domain.User;
import com.sora.result.Result;

/**
 * @Classname UserService
 * @Description
 * @Date 2023/11/22 10:24
 * @Author by Sora33
 */
public interface UserService {

    /**
     * 查询所有用户
     * @return
     */
    Result select(String name, Integer pageNum, Integer pageSize);

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

    /**
     * 根据token获取用户权限
     * @param token
     * @return
     */
    Result permissionsByToken(String token);

    /**
     * 根据id获取用户权限
     * @param id
     * @return
     */
    Result permissionsById(String id);

    /**
     * 根据用户名获取用户
     * @param name
     * @return
     */
    Result selectUserByName(String name);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Result update(User user);
}
