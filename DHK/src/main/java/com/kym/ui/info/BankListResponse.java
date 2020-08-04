package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/6.
 */

public class BankListResponse implements Serializable {
    private ResultInfo result;
    private List<BankInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<BankInfo> getData() {
        return data;
    }

    public void setData(List<BankInfo> data) {
        this.data = data;
    }

    public class BankInfo implements Serializable {

        /**
         * cardid : CDZIZI16A9JE9
         * bank_name : 招商银行
         * bills : 17
         * repayment : 30
         * maxnum : 22
         * bank_no : 6225***********
         */


        private String bank_no;
        private String cardid;
        private String bank_name;
        private int bills = -1;
        private int repayment = -1;
        private int maxnum = -1;
        private String channel;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getDayNum() {
            return DayNum;
        }

        public void setDayNum(int dayNum) {
            DayNum = dayNum;
        }

        private int DayNum = -1;
        private String image_url;
        private String logo_url;
        private String is_sign;
        private String is_sms;
        private String paysource;
        private String balance;


        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getIsUse() {
            return isUse;
        }

        public void setIsUse(int isUse) {
            this.isUse = isUse;
        }

        private int isUse;


        public String getPaysource() {
            return paysource;
        }

        public void setPaysource(String paysource) {
            this.paysource = paysource;
        }

        public String getIs_sms() {
            return is_sms;
        }

        public void setIs_sms(String is_sms) {
            this.is_sms = is_sms;
        }

        private String is_plan;
        private String mobile;
        private String is_xfplan;
        private String is_kadeplan;

        public String getIs_kadeplan() {
            return is_kadeplan;
        }

        public void setIs_kadeplan(String is_kadeplan) {
            this.is_kadeplan = is_kadeplan;
        }

        public String getIs_xfplan() {
            return is_xfplan;
        }

        public void setIs_xfplan(String is_xfplan) {
            this.is_xfplan = is_xfplan;
        }

        //只有首页数据有，计划列表页没有此参数
        private String bill_id;
        private String cash_number;
        private String repay_number;
        private String repay_money;
        private String status;
        private String addtime;
        private String is_allow;
        private String allow_message;

        public String getAllow_message() {
            return allow_message;
        }

        public void setAllow_message(String allow_message) {
            this.allow_message = allow_message;
        }

        public String getIs_allow() {
            return is_allow;
        }

        public void setIs_allow(String is_allow) {
            this.is_allow = is_allow;
        }

        public String getIs_plan() {
            return is_plan;
        }

        public void setIs_plan(String is_plan) {
            this.is_plan = is_plan;
        }

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }

        public String getCash_number() {
            return cash_number;
        }

        public void setCash_number(String cash_number) {
            this.cash_number = cash_number;
        }

        public String getRepay_number() {
            return repay_number;
        }

        public void setRepay_number(String repay_number) {
            this.repay_number = repay_number;
        }

        public String getRepay_money() {
            return repay_money;
        }

        public void setRepay_money(String repay_money) {
            this.repay_money = repay_money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getIs_sign() {
            return is_sign;
        }



        public void setIs_sign(String is_sign) {
            this.is_sign = is_sign;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
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

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }
    }
}
