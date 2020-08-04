package com.kym.ui.newutilutil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BonusOrderDatum {

    @Expose
    private String sn;
    @Expose
    private String name;
    @Expose
    private String mobile;
    @SerializedName("card_no")
    @Expose
    private String cardNo;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @Expose
    private String type;
    @SerializedName("add_time")
    @Expose
    private String addTime;

    /**
     * 
     * @return
     *     The sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * 
     * @param sn
     *     The sn
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 
     * @param cardNo
     *     The card_no
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 
     * @return
     *     The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * @param bankName
     *     The bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The addTime
     */
    public String getAddTime() {
        return addTime;
    }

    /**
     * 
     * @param addTime
     *     The add_time
     */
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}
