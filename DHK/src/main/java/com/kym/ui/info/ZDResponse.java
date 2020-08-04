package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class ZDResponse implements Serializable {

    private ResultInfo result;
    private List<ZDInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<ZDInfo> getData() {
        return data;
    }

    public void setData(List<ZDInfo> data) {
        this.data = data;
    }

    public class ZDInfo implements Serializable {
        private String money;
        private String status;
        private String createtime;
        private String nickname;

        private String bank_name;
        private String bank_no;

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
