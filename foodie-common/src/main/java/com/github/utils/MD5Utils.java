package com.github.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @Author Dooby Kim
 * @Date 2024/2/21 下午11:54
 * @Version 1.0
 */
public class MD5Utils {


    private MD5Utils() {
    }


    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
    }
}
