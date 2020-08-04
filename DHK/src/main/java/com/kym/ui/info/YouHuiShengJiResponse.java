package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class YouHuiShengJiResponse implements Serializable {

    private ResultInfo result;

    public List<YHShengJiInfo> getData() {
        return data;
    }

    public void setData(List<YHShengJiInfo> data) {
        this.data = data;
    }

    private List<YHShengJiInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }



    public class YHShengJiInfo implements Serializable {
        private String top_imgurl;
        private String name;
        private String money;
        private String upgradeLevel;

        public String getUpgradeLevel() {
            return upgradeLevel;
        }

        public void setUpgradeLevel(String upgradeLevel) {
            this.upgradeLevel = upgradeLevel;
        }

        public String getTop_imgurl() {
            return top_imgurl;
        }

        public void setTop_imgurl(String top_imgurl) {
            this.top_imgurl = top_imgurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
