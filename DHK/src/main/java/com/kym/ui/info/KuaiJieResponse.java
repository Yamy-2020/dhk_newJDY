package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class KuaiJieResponse implements Serializable {

    private ResultInfo result;
    private List<KuaiJieInfo> data;

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setData(List<KuaiJieInfo> data) {
        this.data = data;
    }

    public List<KuaiJieInfo> getData() {
        return data;
    }

    public class KuaiJieInfo implements Serializable {
        private String name;
        private String rate;
        private String fee;
        private String single_limit;
        private String day_limit;
        private String id;
        private String level;
        private List<String> bankList;
        private String is_land;
        private String is_new;
        private String name_abb;
        private String isUse;

        public String getIsUse() {
            return isUse;
        }

        public void setIsUse(String isUse) {
            this.isUse = isUse;
        }

        public String getName_abb() {
            return name_abb;
        }

        public void setName_abb(String name_abb) {
            this.name_abb = name_abb;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_land() {
            return is_land;
        }

        public void setIs_land(String is_land) {
            this.is_land = is_land;
        }

        public List<String> getBankList() {
            return bankList;
        }

        public void setBankList(List<String> bankList) {
            this.bankList = bankList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getSingle_limit() {
            return single_limit;
        }

        public void setSingle_limit(String single_limit) {
            this.single_limit = single_limit;
        }

        public String getDay_limit() {
            return day_limit;
        }

        public void setDay_limit(String day_limit) {
            this.day_limit = day_limit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
