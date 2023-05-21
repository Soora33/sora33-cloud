package com.sora.constant;

/**
 * 权限相关通用常量
 *
 */
public class JwtConstants
{


    /**
     * 令牌秘钥
     */
    public final static String SECRET = "minaseinorisakuraayaneeeee";


    /**
     * 用户ID字段
     */
    public static final String USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String USER_NAME = "user_name";

    /**
     * 用户名手机号
     */
    public static final String USER_TEL = "user_tel";

    /**
     * 用户名身份证号
     */
    public static final String ID_CARD = "id_card";

    /**
     * token标识
     */
    public static final String TOKEN = "token";

    /**
     * token标识
     */
    public static final String TOKEN_PRE = "token:";

    /**
     * 登录用户
     */
    public static final String LOGIN_NAME = "login_name";


    /**
     * redis中保存用户token的前缀
     */
    public static final String TOKEN_USER_PREFIX = "user_";
}
