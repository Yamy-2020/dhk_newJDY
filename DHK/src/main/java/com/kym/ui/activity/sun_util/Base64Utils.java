package com.kym.ui.activity.sun_util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author sun
 * @date 2019/11/25 2:17 PM
 */
public class Base64Utils {
    /**
     * 字符Base64加密
     *
     * @param str
     * @return
     */
    public static String encodeToString(String str) {
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符Base64解密
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
