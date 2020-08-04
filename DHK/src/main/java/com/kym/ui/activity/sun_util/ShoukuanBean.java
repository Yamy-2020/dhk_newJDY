package com.kym.ui.activity.sun_util;

/**
 * 收款通道ShoukuanBean
 */

public class ShoukuanBean {

    private String name;
    private int id;
    private String fee;
    private String rate;

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

    public ShoukuanBean(String name, int id, String fee, String rate) {
        this.name = name;
        this.id = id;
        this.fee = fee;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShoukuanBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", fee='" + fee + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
