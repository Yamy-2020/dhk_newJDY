package com.kym.rs.idcard;

import com.google.gson.annotations.Expose;

public class Name {

	@Expose
	private String value;

	/**
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}