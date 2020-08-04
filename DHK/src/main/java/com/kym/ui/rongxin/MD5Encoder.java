package com.kym.ui.rongxin;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {
	
	public static String encode(String string)  {
	    try {
	        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8));
	        StringBuilder hex = new StringBuilder(hash.length * 2);
	        for (byte b : hash) {
	            if ((b & 0xFF) < 0x10) {
	                hex.append("0");
	            }
	            hex.append(Integer.toHexString(b & 0xFF));
	        }
	        return hex.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return null;
	    
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		}
        byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

		public static String MD5(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff;
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0" + hex);
				} else {
					sb.append(hex);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}
	public static String encodeLower(String string)  {
		try {
			byte[] hash = MessageDigest.getInstance("md5").digest(string.getBytes(StandardCharsets.UTF_8));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10) {
					hex.append("0");
				}
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public static String encodeBase64(String string)  {
		String encodedString = Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
		String substring = encodedString.replace("\n", "");
		return substring;
	}
}
