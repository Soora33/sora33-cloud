package com.sora.service.impl;

import com.sora.constant.JwtConstants;
import com.sora.domain.User;
import com.sora.redis.util.RedisUtil;
import com.sora.result.Result;
import com.sora.service.AuthService;
import com.sora.utils.JwtUtils;
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


    private final RedisUtil redisUtil;

    public AuthServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 登陆
     * @param userId
     * @param password
     * @return
     */
    @Override
    public Result selectUserById(String userId, String password) {
        // 从数据库中根据UserId获取用户

        // 对密码进行解密匹配

        // 生成Jwt
        User user = new User(1L, "张三", 29);
        HashMap<String, Object> claimsMap = new HashMap<>() {{
            put(JwtConstants.USER_ID, user.getUserId());
            put(JwtConstants.USER_NAME, user.getName());
        }};
        String token = JwtUtils.createToken(claimsMap);
        // 存入redis，设置过期时间
        redisUtil.set(JwtConstants.TOKEN_USER_PREFIX + userId, token, 30, TimeUnit.MINUTES);
        // 返回
        return Result.success("登陆成功");
    }
}
