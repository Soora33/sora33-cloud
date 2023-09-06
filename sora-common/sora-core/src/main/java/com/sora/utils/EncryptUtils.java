package com.sora.utils;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * @Classname EncryptUtils
 * @Description 加密解密工具类
 * @Date 2023/06/24 14:51
 * @Author by Sora33
 */
public class EncryptUtils {

    /**
     * aes默认值
     */
    private static final String AES_VALUE = "NAYUTASORA331202";

    private static AES aes = null;

    static {
        createAESInstance();
    }


    private static void createAESInstance() {
        // 构建
        AES aes = new AES(AES_VALUE.getBytes());
        EncryptUtils.aes = aes;
    }


    public static String encryAes(String var1) {
        return aes.encryptHex(var1);
    }

    public static String unEncryAes(String var1) {
        return aes.decryptStr(var1);
    }


    public static String encryMD5(String var1) {
        return DigestUtil.md5Hex(var1);
    }

    public static String unEncrySHA1(String var1) {
        return DigestUtil.sha1Hex(var1);
    }

    public static String unEncrySHA256(String var1) {
        return DigestUtil.sha256Hex(var1);
    }

    public static String unEncrySHA512(String var1) {
        return DigestUtil.sha512Hex(var1);
    }

}
