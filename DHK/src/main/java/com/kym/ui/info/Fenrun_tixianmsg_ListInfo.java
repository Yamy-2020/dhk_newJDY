package com.kym.ui.info;

import java.io.Serializable;

public class Fenrun_tixianmsg_ListInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String cash_sum;
	 

	

	

	public String getCash_sum() {
		return cash_sum;
	}

	public void setCash_sum(String cash_sum) {
		this.cash_sum = cash_sum;
	}

	private String add_time;
	private String bank_no;
	private String amount;
	private String fee;
	private String rate;
	private String bank;
	private String status;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
