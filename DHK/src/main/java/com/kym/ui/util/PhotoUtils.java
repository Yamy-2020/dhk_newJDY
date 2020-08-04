package com.kym.ui.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class PhotoUtils {
	public static String path;

	public static Bitmap getRotateBitmap(Bitmap bm, int degree) {
		Bitmap bm1;
		if (degree != 0) {
			android.graphics.Matrix m = new android.graphics.Matrix();
			m.postRotate(degree);
			bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
					m, true);
			return bm1;
		} else {
			return bm;
		}

	}

	public static int getPhotoDegree(String path) {
		ExifInterface exif = null;
		int digree = 0;
		try {
			if (null != path) {
				exif = new ExifInterface(path);
			}
		} catch (IOException e) {
		}
		if (exif != null) {
			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);
			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				digree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				digree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				digree = 270;
				break;
			default:
				digree = 0;
				break;
			}
		}

		return digree;

	}


	public static void compressPhoto() {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得拍照来的bitmap
	 * 
	 */
	public static Bitmap getBitmap(Intent intent) {
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo != null) {
				PhotoUtils.compressPhoto();
				return photo;
			}
		}
		return null;
	}



	public static Bitmap createBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存�?
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;// 获取图片的原始宽�?
			int srcHeight = opts.outHeight;// 获取图片原始高度
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比�?
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长�?
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能�?过inSampleSize来进行缩放，其�?表明缩放的�?数，SDK中建议其值是2的指数�?
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图�?
			return BitmapFactory.decodeFile(path, newOpts);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}


}
