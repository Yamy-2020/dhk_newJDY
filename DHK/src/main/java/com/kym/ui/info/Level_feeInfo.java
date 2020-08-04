package com.kym.ui.info;

public class Level_feeInfo {

	private String id;
	private String name;
	private String fee_upgrade;
	private String split_rate;
	private String deposit;
	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	private String own_rate;
	private String head_img;
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	private String level;
	private int is_update;
	
	public String getOwn_rate() {
		return own_rate;
	}

	public void setOwn_rate(String own_rate) {
		this.own_rate = own_rate;
	}

	public int getIs_update() {
		return is_update;
	}

	public void setIs_update(int is_update) {
		this.is_update = is_update;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getFee_upgrade() {
		return fee_upgrade;
	}

	public void setFee_upgrade(String fee_upgrade) {
		this.fee_upgrade = fee_upgrade;
	}

	public String getSplit_rate() {
		return split_rate;
	}

	public void setSplit_rate(String split_rate) {
		this.split_rate = split_rate;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

}
