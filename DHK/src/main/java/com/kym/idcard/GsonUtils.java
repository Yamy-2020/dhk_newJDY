package com.kym.idcard;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public final class GsonUtils {

    private static Gson gson = new Gson();

    public static <T> T parseJSON(String arg0, Class<T> clazz) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(arg0, clazz);
    }

    /**
     * Type type = news_img
     * TypeToken&lt;ArrayList&lt;TypeInfo>>(){}.getType();
     * <br>Type所在的包：java.lang.reflect
     * <br>TypeToken所在的包：com.google.gson.reflect.TypeToken
     *
     * @param jsonArr
     * @param type
     * @return
     */
    public static <T> T parseJSONArray(String jsonArr, Type type) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(jsonArr, type);
    }


    private GsonUtils() {
    }
}
