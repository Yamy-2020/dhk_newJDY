package com.kym.ui.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json转化工具类
 * Created by zachary on 2018/1/15.
 */

public class JsonUtils {
    private static Gson gson;

    /**
     * 将String类型转化成对象
     */
    public static Object parse(String result, Class clazz) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(result, clazz);
    }

    /**
     * 将对象转化成Json字符串
     */
    public static String toJson(Object o) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(o);
    }

    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
