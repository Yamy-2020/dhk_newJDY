package com.kym.ui.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public final class SysUtils
{

	public static boolean isAvilible(Context context, String packageName){
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<>();
		if(pinfo != null){  
			for(int i = 0; i < pinfo.size(); i++){    
				String pn = pinfo.get(i).packageName;   
				pName.add(pn);  
				}
			}         
		return pName.contains(packageName);
	    }


}
