package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HandleCardData {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @Expose
    private java.util.List<HandleCardList> list = new ArrayList<HandleCardList>();

    /**
     * 
     * @return
     *     The bannerImg
     */
    public String getBannerImg() {
        return bannerImg;
    }

    /**
     * 
     * @param bannerImg
     *     The banner_img
     */
    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<HandleCardList> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<HandleCardList> list) {
        this.list = list;
    }

}
