package com.kym.ui.util;

import com.google.gson.annotations.Expose;
import com.kym.ui.newutil.Result;

public class UpLoadImage {

	@Expose
	private Result result;
	@Expose
	private UpLoadImageData data;

	/**
	 * 
	 * @return The result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 *            The result
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * 
	 * @return The data
	 */
	public UpLoadImageData getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 *            The data
	 */
	public void setData(UpLoadImageData data) {
		this.data = data;
	}

}
