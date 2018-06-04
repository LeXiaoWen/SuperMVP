package com.leo.mvp.data;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 用于MD5加密
 * created by Leo on 2018/6/5 00 : 12
 */


public class MD5 {
    /**
    * 获取32位随机数
    *
    *@author Leo
    *created at 2018/6/5 上午12:12
    */
    public String get32() {
        UUID uuid = UUID.randomUUID();
        String randow = uuid.toString().replace("-", "");
        return randow;
    }

    /**
    * 将字符串使用MD5加密
    *
    *@author Leo
    *created at 2018/6/5 上午12:12
    */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
