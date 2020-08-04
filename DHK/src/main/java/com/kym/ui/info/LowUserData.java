package com.kym.ui.info;

import java.util.List;

public class LowUserData {

	private List<UserChildAccount> list;  //下属商户列表
	private String cash_total;  //总交易金额
	private String split;  //分润总金额
	public List<UserChildAccount> getList() {
		return list;
	}
	public void setList(List<UserChildAccount> list) {
		this.list = list;
	}
	public String getCash_total() {
		return cash_total;
	}
	public void setCash_total(String cash_total) {
		this.cash_total = cash_total;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	

	
	
}
