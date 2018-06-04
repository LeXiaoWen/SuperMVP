package com.leo.mvp.data;

import com.leo.mvp.utils.codec.BASE64Decoder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * 加解密Base64
 * created by Leo on 2018/6/5 00 : 13
 */


public class IDEACode {

    /**
     * 密钥算法
     * */
    public static final String KEY_ALGORITHM="AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * */
    public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";

    public static final String SECRET_KEY = "2i2igumvap16vIhwhaH7qA==";


    /**
     *
     * 生成密钥，只有bouncycastle支持
     * @return byte[] 二进制密钥
     * */
    public static byte[] initkey() throws Exception{
        //加入bouncyCastle支持
//        Security.addProvider(new BouncyCastleProvider());

        //实例化密钥生成器
        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器，IDEA要求密钥长度为128位
        kg.init(128);
        //生成密钥
        SecretKey secretKey=kg.generateKey();
        //获取二进制密钥编码形式
        return secretKey.getEncoded();
    }
    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return Key 密钥
     * */
    private static Key toKey(byte[] key) throws Exception{
        //实例化DES密钥
        //生成密钥
        SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密后的数据
     * */
    private static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //加入bouncyCastle支持
//        Security.addProvider(new BouncyCastleProvider());
        //还原密钥
        Key k=toKey(key);
        //实例化
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }
    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密后的数据
     * */
    private static byte[] decrypt(byte[] data,byte[] key) throws Exception{

        // 加入BouncyCastleProvider支持
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        if(null != Security.getProvider(bouncyCastleProvider.getName()))
        {
            Security.removeProvider(bouncyCastleProvider.getName());
        }
        Security.addProvider(bouncyCastleProvider);

        //还原密钥
        Key k =toKey(key);
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     *@author Leo
     *created at 2017/11/14 下午4:26
     */
    public static String ideaDecrypt(String data) {
        byte[] bytes = data.getBytes();
        BASE64Decoder base64Decoder = new BASE64Decoder();


        String result = null;
        try {
            byte[] data_de =IDEACode.decrypt(base64Decoder.decodeBuffer(data), base64Decoder.decodeBuffer(SECRET_KEY));
            result = new String(data_de);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null){
            return result;
        }else
            return null;
    }
}
