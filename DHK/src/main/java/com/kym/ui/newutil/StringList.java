package com.kym.ui.newutil;

public class StringList {
	private String s;
	private String id;

	public StringList(String s) {
		super();
		this.s = s;
	}

	public StringList(String s, String id) {
		super();
		this.s = s;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
}
