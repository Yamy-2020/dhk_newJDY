package com.kym.ui.appconfig;

import android.util.Log;

public class log {

	private static  boolean isOutput = true;

	
	/**
	 * ��Ϣ 
	 * @param s
	 */
	public static void i(String s) {
		if (isOutput) {
			Log.i(getTraceInfo(), s);
		}
	}

	/**
	 * ����
	 * @param s
	 */
	public static void e(String s) {
		if (isOutput) {
			Log.e(getTraceInfo(), s);
		}
	}

	/**
	 * debug
	 * @param s
	 */
	public static void d(String s) {
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
	 * ����
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
