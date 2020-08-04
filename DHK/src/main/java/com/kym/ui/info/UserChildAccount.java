package com.kym.ui.info;

public class UserChildAccount {

	private String usid; //商户id 
	private String mobile;  
	private String us_name; //商户名称
	private String lf_name; //等级名称
	private String lf_type; //等级类型
	private String account; //交易总额
	private String split; //为上级商户带来的分润收入
	
	private String totalaccount;
	private String totalsplit;
	private String size;
	private String ordin;
	private String diamon;
	
	
	
	
	
	public String getTotalaccount() {
		return totalaccount;
	}
	public void setTotalaccount(String totalaccount) {
		this.totalaccount = totalaccount;
	}
	public String getTotalsplit() {
		return totalsplit;
	}
	public void setTotalsplit(String totalsplit) {
		this.totalsplit = totalsplit;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOrdin() {
		return ordin;
	}
	public void setOrdin(String ordin) {
		this.ordin = ordin;
	}
	public String getDiamon() {
		return diamon;
	}
	public void setDiamon(String diamon) {
		this.diamon = diamon;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsid() {
		return usid;
	}
	public void setUsid(String usid) {
		this.usid = usid;
	}
	public String getUs_name() {
		return us_name;
	}
	public void setUs_name(String us_name) {
		this.us_name = us_name;
	}
	public String getLf_name() {
		return lf_name;
	}
	public void setLf_name(String lf_name) {
		this.lf_name = lf_name;
	}
	public String getLf_type() {
		return lf_type;
	}
	public void setLf_type(String lf_type) {
		this.lf_type = lf_type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	
	
}
