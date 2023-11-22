package com.sora.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sora.common.UserConstant;
import com.sora.domain.User;
import com.sora.mapper.UserMapper;
import com.sora.result.Result;
import com.sora.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Date 2023/11/22 10:24
 * @Author by Sora33
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * 查询用户
     * @return
     */
    @Override
    public Result select() {
        return Result.success(userMapper.selectList(new QueryWrapper<>()));
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Result insert(User user) {
        // 判断是否重复名字
        List<User> dataUser = userMapper.selectList(new QueryWrapper<User>().lambda()
                .eq(User::getName, user.getName()));
        if (!dataUser.isEmpty()) {
            return Result.error("用户名重复！");
        }
        // 对用户的密码进行加盐加密
        String pwd = bCryptPasswordEncoder.encode(user.getPassword() + UserConstant.SLAT);
        user.setPassword(pwd);
        user.setId(IdUtil.getSnowflakeNextIdStr());
        user.setCreateTime(new Date());
        return userMapper.insert(user) > 0 ? Result.success("注册成功！") : Result.error("注册失败，请稍后重试！");
    }


    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    @Override
    public Result login(String name, String password) {
        // 根据name获取用户
        List<User> user = userMapper.selectList(new QueryWrapper<User>().lambda()
                        .eq(User::getName, name));
        if (user.isEmpty()) {
            return Result.error("用户不存在！");
        }
        // 密码验证
        if (bCryptPasswordEncoder.matches(password + UserConstant.SLAT, user.get(0).getPassword())) {
            return Result.success(user.get(0),"登陆成功！");
        }
        return Result.error("密码错误！");
    }
}
