package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerBottom {

    @Expose
    private String name;
    @Expose
    private String remark;
    @Expose
    private String amount;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("jump_url")
    @Expose
    private String jumpUrl;

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
     *     The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark
     *     The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 
     * @param imgUrl
     *     The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 
     * @return
     *     The jumpUrl
     */
    public String getJumpUrl() {
        return jumpUrl;
    }

    /**
     * 
     * @param jumpUrl
     *     The jump_url
     */
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

}
