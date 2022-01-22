package com.jfeat.am.power.base.numberCheck.impl;

import javax.crypto.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by craftperson on 2018/1/19.
 */
public class AESEncoder {
    private static String SHA1PRNG = "SHA1PRNG";
    private static  String AES="AES";
    private static String CHARSET = "utf-8";
    private static String encodeRules = "power";


    /*
     * AES对称加密
     * encodeRules 加密规则
     * content 加密字符串
     */
    public static  String AESEncoder(String content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
        byte[] byte_content = content.getBytes(CHARSET);//为了避免乱码，设置成utf-8，当然其他格式也行
        byte[] byte_AES = cipher.doFinal(byte_content);//对内容进行加密
        String AES_encode = new String(Base64.getEncoder().encodeToString(byte_AES));//把获取的加密字节重新封装为字符串输出
        return  AES_encode;
    }

    public static String AESDecode(String encodeContent) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
        byte [] byte_content= Base64.getDecoder().decode(encodeContent);
        byte[] content = cipher.doFinal(byte_content);
        return new String(content,CHARSET);
    }

    private static Cipher initCipher(Integer mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Key secretKey = getKey();//创建秘钥
        Cipher cipher = Cipher.getInstance(AES);//创建AES类型的密码器
        cipher.init(mode,secretKey);//初始化密码器，输入加密的秘钥
        return cipher;
    }


    public static Key getKey() throws NoSuchAlgorithmException {
            if (encodeRules == null) {
                encodeRules = "";
            }
            KeyGenerator _generator = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            secureRandom.setSeed(encodeRules.getBytes());
            _generator.init(128, secureRandom);//初始化生成器，使用128位加密，也可以192,256位
            return _generator.generateKey();
    }

}
