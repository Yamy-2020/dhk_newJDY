package com.kym.ui.info;

import java.io.Serializable;

public class NewJhDcResponse implements Serializable {
    private ResultInfo result;
    private NewJhDcInfo data;

    public NewJhDcInfo getData() {
        return data;
    }

    public void setData(NewJhDcInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public class NewJhDcInfo {
        private String is_open;
        private String day_quota;
        private String one_quota;
        private String stoptime;
        private String bill;
        private String is_black;
        private String is_kade_fail;

        public String getIs_black() {
            return is_black;
        }

        public void setIs_black(String is_black) {
            this.is_black = is_black;
        }

        public String getIs_kade_fail() {
            return is_kade_fail;
        }

        public void setIs_kade_fail(String is_kade_fail) {
            this.is_kade_fail = is_kade_fail;
        }

        public String getBill() {
            return bill;
        }

        public void setBill(String bill) {
            this.bill = bill;
        }

        public String getStoptime() {
            return stoptime;
        }

        public void setStoptime(String stoptime) {
            this.stoptime = stoptime;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public String getDay_quota() {
            return day_quota;
        }

        public void setDay_quota(String day_quota) {
            this.day_quota = day_quota;
        }

        public String getOne_quota() {
            return one_quota;
        }

        public void setOne_quota(String one_quota) {
            this.one_quota = one_quota;
        }
    }
}