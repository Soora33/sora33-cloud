package com.sora;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.*;

/**
 * @Classname EncryTest
 * @Description
 * @Date 2023/06/24 15:00
 * @Author by Sora33
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncryTest {

    private final static Logger logger = LoggerFactory.getLogger(EncryTest.class);

    @Test
    public void AESTest() {

        // 创建AES对象 并传入密钥
        AES aes = new AES("NAYUTASORA331202".getBytes());

        String str = "Sora33";

        // 加密
        String encryptHex = aes.encryptHex(str);
        logger.info("加密的字符串：[{}]", str);
        logger.info("AES加密后的字符串：[{}]", encryptHex);

        // 加密
        String decryptStr = aes.decryptStr(encryptHex);
        logger.info("AES解密后的字符串：[{}]", decryptStr);

    }


    @Test
    public void RSATest() {

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK4u5HSXON2sq8kiAQ8AvvLZMfYCCy22g4kbTMqG25eodeYzkVSfua8vSjViqwQ6HnjmMPZAxVE+cOOeky1TQFSmM8imHc/+q5aPlQHGNLKyhCkApyGYWdD2s1BtX1hBcxKOQww00B2NqrVMu1ykbXE7YyFJ0/+Bz81q0pl7oexzAgMBAAECgYALcnN7IhEPqHBluIFfTgo+hX2eEEZRy8PbN9sVGEXIMr8E0PDFIfYfCDmVRpW8omEsStx+4oTVMQhUPTCo8uawTaM6t4bHlb88U7m4qEB4xX2316GlnxG2An6ib8l94yW0EcIjdPMxOMUBWAFPDhjIk0FUxt1K/GxQ5W2GMhLXyQJBAONQTdN855EG+UK81Fjskvi7Zkpo+3S0I9o/MEBilgbSU+m2UVzMRY3Y9Umjgz7wPTiGz9b3JJsymCmzaEUa5/cCQQDEKiFxPUhy8ZUZmxybhFVXU1m5L4MeCkzeXKbWY+c4kmhAcY3I0diYj9ORDVM4cc9/vuT2ZSfUpVUCcvaDFNhlAkAl2RkcPY/Q9fhKxGYW6E0QXSOLAC/eHqBZlmvSTJfuStbt8w1ZBioOlDFDMZaIxDdtUgUJJd1Sefob92NFHlXBAkEAsz9AMcZq5kVkFfLLsDu688HBEduddxy4YtPMy8icJvB5fLGGeoNt5PI/w6KmccRlc/iOJawHOmMdC9Da+qpYlQJAXGw42Lgs+/UEqBtqs5PyXuzatwB1Q4EBIVVLkOXqqggM2WywQgXjm2NPAJ0E7LZtG+BEvCjhJj7FT3cEJ4g7gQ==";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuLuR0lzjdrKvJIgEPAL7y2TH2AgsttoOJG0zKhtuXqHXmM5FUn7mvL0o1YqsEOh545jD2QMVRPnDjnpMtU0BUpjPIph3P/quWj5UBxjSysoQpAKchmFnQ9rNQbV9YQXMSjkMMNNAdjaq1TLtcpG1xO2MhSdP/gc/NatKZe6HscwIDAQAB";

        // 创建RSA对象
        RSA rsa = SecureUtil.rsa(privateKey,publicKey);

        String str = "Sora33";

        // 加密
        byte[] encryptData = rsa.encrypt(str, KeyType.PublicKey);
        String encryptStr = Base64.encode(encryptData);
        logger.info("加密后的字符串：" + encryptStr);

        // 解密
        byte[] decryptData = rsa.decrypt(Base64.decode(encryptStr), KeyType.PrivateKey);
        String decryptStr = new String(decryptData);
        logger.info("解密后的字符串：" + decryptStr);
    }

    @Test
    public void md5Test() {
        String str = "Sora33";
        String slat = "TOOOONAYUTAAAAAA";

        // 计算SHA256哈希值
        String sha256Hex = DigestUtil.md5Hex(str + slat);

        // 打印SHA256哈希值
        logger.info("MD5 Hash: " + sha256Hex);
    }

    @Test
    public void SHA256Test() {
        String str = "Sora33";
        String slat = "TOOOONAYUTAAAAAA";

        // 计算SHA256哈希值
        String sha256Hex = DigestUtil.sha256Hex(str + slat);

        // 打印SHA256哈希值
        logger.info("SHA256 Hash: " + sha256Hex);
    }

    @Test
    public void a () {
        // 添加 Bouncy Castle 提供者
        Security.addProvider(new BouncyCastleProvider());

        // 创建密钥对生成器
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(256); // 指定密钥长度

        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 获取公钥和私钥的字节数组
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

        // 将字节数组转换为十六进制字符串表示
        String publicKeyHex = Hex.toHexString(publicKeyBytes);
        String privateKeyHex = Hex.toHexString(privateKeyBytes);

        // 打印公钥和私钥
        System.out.println("Public Key: " + publicKeyHex);
        System.out.println("Private Key: " + privateKeyHex);
    }
}
