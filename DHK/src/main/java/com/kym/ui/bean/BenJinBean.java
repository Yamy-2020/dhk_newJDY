package com.kym.ui.bean;


import com.contrarywind.interfaces.IPickerViewData;


public class BenJinBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":[{name:"东城区"},{name:"西城区"}]}]
     */

    private String type;
    private String money;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String getPickerViewText() {
        return this.money;
    }
}
