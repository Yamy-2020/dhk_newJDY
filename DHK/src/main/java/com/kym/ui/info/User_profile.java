package com.kym.ui.info;

import android.os.Parcel;
import android.os.Parcelable;

public class User_profile implements Parcelable{

	/**
	 * 
	 */
//	private String id;
//	private String sn;
//	private String pid;
//	private String depth_pid;
//	private String mobile;
//	private String password;
//	private String tradePassword;
//	private String name;
//	private String card_no;
//	private String lpid;
//	private String lsid;
//	private String lfid;
//	private String lfid_lock;
//	private String provincename;
//	private String cityname;
//	private String bank;
//	private String bankcode;
//	private String bank_name;
//	private String bank_no;
//	private String status;
//	private String addtime;
//	private String sharelink;
//	private String check_status;
//	private String collect;
//	private String splitter_total;
//	private String splitter_remain;
	

	

	public String getCash_total() {
		return cash_total;
	}

	public void setCash_total(String cash_total) {
		this.cash_total = cash_total;
	}

	public String getOs_type() {
		return os_type;
	}

	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}

	private String cash_total;
	private String os_type;


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String id;
	private String mobile;
	private String name;
	private String card_no;
	
	private String bank;
	private String bank_name;
	private String bank_no;
	private String bank_address;
	private String status;
	//private String status_shop;
	private User_authInfo auth_file;
	private Level_feeInfo level;
	private String province_id;  //省
	private String city_id;  //市
	private String area_id;  //区
	private String address;  //街道地址
	//private int lfid_lock;//等级锁定，大于0表示被动升级
	private String invite_reg_url;  //邀请注册地址

	
	
	private String provincename;  
	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	private String cityname;  

	
	public String getInvite_reg_url() {
		return invite_reg_url;
	}

	public void setInvite_reg_url(String invite_reg_url) {
		this.invite_reg_url = invite_reg_url;
	}

//	public int getLfid_lock() {
//		return lfid_lock;
//	}
//
//	public void setLfid_lock(int lfid_lock) {
//		this.lfid_lock = lfid_lock;
//	}

	
   
	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Level_feeInfo getLevel() {
		return level;
	}

	public void setLevel(Level_feeInfo level) {
		this.level = level;
	}

	public User_authInfo getAuth_file() {
		return auth_file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public String getStatus_shop() {
//		return status_shop;
//	}
//
//	public void setStatus_shop(String status_shop) {
//		this.status_shop = status_shop;
//	}

	public User_authInfo getAuth_filee() {
		return auth_file;
	}

	public void setAuth_file(User_authInfo auth_file) {
		this.auth_file = auth_file;
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
	}

}
