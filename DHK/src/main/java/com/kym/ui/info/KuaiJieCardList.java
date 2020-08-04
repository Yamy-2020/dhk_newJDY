package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class KuaiJieCardList implements Serializable {
    private ResultInfo result;
    private List<KuaiJieCardListInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<KuaiJieCardListInfo> getData() {
        return data;
    }

    public void setData(List<KuaiJieCardListInfo> data) {
        this.data = data;
    }

    public class KuaiJieCardListInfo implements Serializable {
        private String bank_no;
        private String cardid;
        private String bank_name;
        private int bills;
        private int repayment;
        private int maxnum;
        private int DayNum;
        private String image_url;
        private String logo_url;
        private String channel;
        private String balance;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        private String bank_contract;

        public String getBank_contract() {
            return bank_contract;
        }

        public void setBank_contract(String bank_contract) {
            this.bank_contract = bank_contract;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public int getBills() {
            return bills;
        }

        public void setBills(int bills) {
            this.bills = bills;
        }

        public int getRepayment() {
            return repayment;
        }

        public void setRepayment(int repayment) {
            this.repayment = repayment;
        }

        public int getMaxnum() {
            return maxnum;
        }

        public void setMaxnum(int maxnum) {
            this.maxnum = maxnum;
        }

        public int getDayNum() {
            return DayNum;
        }

        public void setDayNum(int dayNum) {
            DayNum = dayNum;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

    }
}
