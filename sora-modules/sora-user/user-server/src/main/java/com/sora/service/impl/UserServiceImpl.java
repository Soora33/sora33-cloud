package com.sora.service.impl;

import cn.hutool.core.util.IdUtil;
import com.mybatisflex.core.query.QueryChain;
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

import static com.sora.domain.table.UserTableDef.USER;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Date 2023/11/22 10:24
 * @Author by Sora33
 */
@Service
public class UserServiceImpl implements UserService {

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
        List<User> userList = QueryChain.of(userMapper).list();
        return Result.success(userList);
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Result insert(User user) {
        // 判断是否重复名字
        List<User> userList = QueryChain.of(userMapper)
                .where(USER.NAME.eq(user.getName()))
                .limit(1)
                .list();
        if (!userList.isEmpty()) {
            return Result.error("用户名重复！");
        }
        // 对用户的密码进行加盐加密
        String pwd = bCryptPasswordEncoder.encode(user.getPassword() + UserConstant.SLAT);
        user.setPassword(pwd);
        user.setId(IdUtil.getSnowflakeNextIdStr());
        user.setCreateTime(new Date());
        return userMapper.insertSelective(user) > 0 ? Result.success(null,"注册成功！") : Result.error("注册失败，请稍后重试！");
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
        List<User> userList = QueryChain.of(userMapper)
                .where(USER.NAME.eq(name))
                .limit(1)
                .list();
        if (userList.isEmpty()) {
            return Result.error("用户不存在！");
        }
        // 密码验证
        if (bCryptPasswordEncoder.matches(password + UserConstant.SLAT, userList.get(0).getPassword())) {
            return Result.success(userList.get(0),"登陆成功！");
        }
        return Result.error("密码错误！");
    }
}
