package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class BenJinZhangHu implements Serializable {
    private ResultInfo result;
    private List<BenJInZhangHuInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<BenJInZhangHuInfo> getData() {
        return data;
    }

    public void setData(List<BenJInZhangHuInfo> data) {
        this.data = data;
    }

    public class BenJInZhangHuInfo implements  Serializable {
        private String type;
        private String money;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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
    }
}
