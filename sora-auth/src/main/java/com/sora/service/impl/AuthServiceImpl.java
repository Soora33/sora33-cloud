package com.sora.service.impl;

import com.sora.UserFeign;
import com.sora.constant.JwtConstants;
import com.sora.constant.LogConstants;
import com.sora.domain.User;
import com.sora.redis.util.RedisUtil;
import com.sora.result.Result;
import com.sora.service.AuthService;
import com.sora.utils.JwtUtils;
import com.sora.utils.ServletUtils;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Classname AuthServiceImpl
 * @Description
 * @Date 2023/05/21 20:41
 * @Author by Sora33
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final RedisUtil redisUtil;

    private final UserFeign userFeign;

    public AuthServiceImpl(RedisUtil redisUtil, UserFeign userFeign) {
        this.redisUtil = redisUtil;
        this.userFeign = userFeign;
    }

    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    @Override
    public Result login(String name, String password) {
        Result<User> responseUser = userFeign.login(name, password);
        if (responseUser.getCode() != 200) {
            return responseUser;
        }
        User user = responseUser.getData();
        // 创建token并存入redis
        HashMap<String, Object> claimsMap = new HashMap<>() {{
            put(JwtConstants.USER_ID, user.getId());
            put(JwtConstants.USER_NAME, user.getName());
        }};
        String token = JwtUtils.createToken(claimsMap);
        // 存入redis，设置过期时间
        redisUtil.set(JwtConstants.TOKEN_USER_PREFIX + user.getId(), token, 30, TimeUnit.MINUTES);
        logger.info("{}：用户[{}]登陆成功", LogConstants.LOGIN_LOG, user.getName());
        // 保存到cookie
        Cookie cookie = new Cookie("userId", user.getId());
        // 有效期为半小时
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        ServletUtils.getResponse().addCookie(cookie);
        return Result.success(null,"登陆成功");
    }


    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        return userFeign.register(user);
    }
}
