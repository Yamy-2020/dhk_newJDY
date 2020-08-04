package com.kym.ui.util;

import android.util.Log;

public class log {

	private static  boolean isOutput = true;

	
	/**
	 * 信息 
	 * @param s
	 */
	public static void i(String s) {
		if (isOutput) {
			Log.i(getTraceInfo(), s);
		}
	}

	/**
	 * 错误
	 * @param s
	 */
	public static void e(String s) {
		if (isOutput) {
			Log.e(getTraceInfo(), s);
		}
	}

	/**
	 * debug
     * @param 账单明细
     * @param s
     */
	public static void d(String 账单明细, String s) {
		if (isOutput) {
			Log.d(getTraceInfo(), s);
		}
	}

	/**
	 * Verbose
	 * @param s
	 */
	public static void v(String s) {
		if (isOutput) {
			Log.v(getTraceInfo(), s);
		}
	}

	/**
	 * 警告
	 * @param s
	 */
	public static void w(String s) {
		if (isOutput) {
			Log.w(getTraceInfo(), s);
		}
	}

	public static String getTraceInfo() {
		StringBuffer sb = new StringBuffer();

		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append(stacks[2].getClassName()).append(".")
				.append(stacks[2].getLineNumber());

		return sb.toString();
	}
}
