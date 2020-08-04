package com.kym.ui.info;

public class RegisterInfo {

    private String mobile;//手机号码
    private String password;//密码
    private String code;  //短信验证码
    private String invite_mobile; //邀请人的手机号
    private String lfid;  //等级ID
    private String type;  //注册类型

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    private String device; //设备

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    private String cid;  //个推

    public String getCurrentProvinces() {
        return currentProvinces;
    }

    public void setCurrentProvinces(String currentProvinces) {
        this.currentProvinces = currentProvinces;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIs_openposition() {
        return is_openposition;
    }

    public void setIs_openposition(String is_openposition) {
        this.is_openposition = is_openposition;
    }

    private String ptid;  //支付方式
    private String p;
    private String accessToken;
    private String currentProvinces;
    private String longitude;
    private String currentCity;
    private String latitude;
    private String is_openposition;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String card_no;//卡号
    private String cityid;//城市


    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public RegisterInfo() {
        super();

    }

    public RegisterInfo(String mobile, String password, String code,
                        String invite_mobile, String lfid, String type) {
        super();
        this.mobile = mobile;
        this.password = password;
        this.code = code;
        this.invite_mobile = invite_mobile;
        this.lfid = lfid;
        this.type = type;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInvite_mobile() {
        return invite_mobile;
    }

    public void setInvite_mobile(String invite_mobile) {
        this.invite_mobile = invite_mobile;
    }

    public String getLfid() {
        return lfid;
    }

    public void setLfid(String lfid) {
        this.lfid = lfid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPtid() {
        return ptid;
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
    }


}
