package com.kym.ui.rongxin;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/9/18.
 */

public class SignUtil {

	public static String getSign(LinkedHashMap<String, String> map) {
		String sign = "";
		for (String key : map.keySet()) {
			sign +=  map.get(key);
		}
		String substring = sign;
		return substring.replace(" ","").replace("\n", "").replace("\r", "");
//        return substring;
	}
}
