package com.kym.ui.info;

import android.content.Context;

public class RequestParam {
	
	private String url;
	private Object params;
	private Context context;
	private int flag;
	
	public RequestParam() {
		super();
		
	}
	public RequestParam(String url, Object params, Context context,int flag) {
		super();
		this.url = url;
		this.params = params;
		this.context = context;
		this.flag=flag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getParams() {
		return params;
	}
	public void setParams(Object params) {
		this.params = params;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	

}
