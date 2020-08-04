package com.kym.ui.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
/**
 *
 * 自定义，可删除
 * */
    public static final String ROOT_NAME = "RongYifu";
    public static final String CACHE_NAME = "Cache";
    public static final String IMAGE_NAME = "Image";

    public static final String ROOT_PATH = File.separator + ROOT_NAME
            + File.separator;
    public static final String CACHE_PATH_NAME = File.separator + CACHE_NAME
            + File.separator;
    public static final String IMAGE_PATH_NAME = File.separator + IMAGE_NAME
            + File.separator;

    public static String getRootPath(Context appContext) {

        String rootPath;
        if (checkMounted()) {
            rootPath = getRootPathOnSdcard();
        } else {
            rootPath = getRootPathOnPhone(appContext);
        }
        return rootPath;
    }

    public static String getRootPathOnSdcard() {
        File sdcard = Environment.getExternalStorageDirectory();
        String rootPath = sdcard.getAbsolutePath() + ROOT_PATH;
        return rootPath;
    }

    public static String getRootPathOnPhone(Context appContext) {
        File phoneFiles = appContext.getFilesDir();
        String rootPath = phoneFiles.getAbsolutePath() + ROOT_PATH;
        return rootPath;
    }

    public static boolean checkMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    // 缓存整体路径
    public static String getCacheDirPath(Context appContext) {

        String imagePath = getRootPath(appContext) + CACHE_PATH_NAME;
        return imagePath;
    }

    // 图片缓存路径
    public static String getImageCacheDirPath(Context appContext) {

        String imagePath = getCacheDirPath(appContext) + IMAGE_PATH_NAME;
        return imagePath;
    }

    // 创建一个图片文件
    public static File getImgFile(Context context) {
        File file = new File(getImageCacheDirPath(context));
        if (!file.exists()) {
            file.mkdirs();
        }
        String imgName = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File imgFile = new File(file.getAbsolutePath() + File.separator
                + "IMG_" + imgName + ".jpg");
        return imgFile;
    }


}
