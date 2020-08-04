package com.leon.commons.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * 
 *  获以应用软件的版本号
 * 
 * @author Leon_Chen
 *
 */
public class ApplicationUtil {

	
	@SuppressLint("WrongConstant")
	public static int getLocalVersionCode(Context context) {
		int versionCode = 1;
		
		try {
			versionCode = context.getPackageManager().getPackageInfo(getPackageName(context), 1).versionCode;
		} catch(NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return versionCode;
	}

	public static String getPackageName(Context context){
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		String packageNames = info.packageName;  
		
		return packageNames;
	}
}
