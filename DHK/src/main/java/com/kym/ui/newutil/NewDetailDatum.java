package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDetailDatum {
    public String getOmid() {
        return omid;
    }

    public void setOmid(String omid) {
        this.omid = omid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Expose
    private String omid;
    @Expose
    private String uid;
    @Expose
    private String id;
    @Expose
    private String mid;
    @Expose
    private String name;
    @Expose
    private String mobile;
    @Expose
    private String usid;
    @SerializedName("total_money")
    @Expose
    private String totalMoney;
    @SerializedName("collection_moey")
    @Expose
    private String collectionMoey;
    @Expose
    private String addtime;
    private String total_cash_money;
    @SerializedName("total_receive_money")
    @Expose
    private String total_receive_money;
    @SerializedName("total_kade_money")
    @Expose
    private String total_kade_money;

    private String total_update_money;

    private String total_active_cash_money;

    public String getTotal_active_cash_money() {
        return total_active_cash_money;
    }

    public void setTotal_active_cash_money(String total_active_cash_money) {
        this.total_active_cash_money = total_active_cash_money;
    }

    public String getTotal_update_money() {
        return total_update_money;
    }

    public void setTotal_update_money(String total_update_money) {
        this.total_update_money = total_update_money;
    }

    public String getTotal_kade_money() {
        return total_kade_money;
    }

    public void setTotal_kade_money(String total_kade_money) {
        this.total_kade_money = total_kade_money;
    }

    public String getTotal_receive_money() {
        return total_receive_money;
    }

    public void setTotal_receive_money(String total_receive_money) {
        this.total_receive_money = total_receive_money;
    }

    public String getTotal_cash_money() {
        return total_cash_money;
    }

    public void setTotal_cash_money(String total_cash_money) {
        this.total_cash_money = total_cash_money;
    }


    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return The usid
     */
    public String getUsid() {
        return usid;
    }

    /**
     * @param usid The usid
     */
    public void setUsid(String usid) {
        this.usid = usid;
    }

    /**
     * @return The totalMoney
     */
    public String getTotalMoney() {
        return totalMoney;
    }

    /**
     * @param totalMoney The total_money
     */
    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * @return The collectionMoey
     */
    public String getCollectionMoey() {
        return collectionMoey;
    }

    /**
     * @param collectionMoey The collection_moey
     */
    public void setCollectionMoey(String collectionMoey) {
        this.collectionMoey = collectionMoey;
    }

    /**
     * @return The addtime
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     * @param addtime The addtime
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

}
