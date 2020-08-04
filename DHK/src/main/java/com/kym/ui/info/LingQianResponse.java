package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class LingQianResponse implements Serializable {
    private ResultInfo result;
    private List<LingQianInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<LingQianInfo> getData() {
        return data;
    }

    public void setData(List<LingQianInfo> data) {
        this.data = data;
    }

    public class LingQianInfo implements Serializable {
        private String type;
        private String money;
        private String addtime;

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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
