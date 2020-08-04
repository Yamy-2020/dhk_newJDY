package com.kym.ui.info;

import java.util.List;

public class DaiChangRecord {
    private ResultBean result;
    private List<DataBean> data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ResultBean{

        /**
         * code : 10000
         * msg : 请求成功
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean{
        private String payment_total_number; //还款笔数
        private String consumpt_total_number; //消费笔数
        private String payment_total_money; //还款金额
        private String poundage_total_fee; //手续费
        private String bank_name; //银行名称
        private String afterafew; //尾号
        private String status;
        private String bill_id;

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayment_total_number() {
            return payment_total_number;
        }

        public void setPayment_total_number(String payment_total_number) {
            this.payment_total_number = payment_total_number;
        }

        public String getConsumpt_total_number() {
            return consumpt_total_number;
        }

        public void setConsumpt_total_number(String consumpt_total_number) {
            this.consumpt_total_number = consumpt_total_number;
        }

        public String getPayment_total_money() {
            return payment_total_money;
        }

        public void setPayment_total_money(String payment_total_money) {
            this.payment_total_money = payment_total_money;
        }

        public String getPoundage_total_fee() {
            return poundage_total_fee;
        }

        public void setPoundage_total_fee(String poundage_total_fee) {
            this.poundage_total_fee = poundage_total_fee;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAfterafew() {
            return afterafew;
        }

        public void setAfterafew(String afterafew) {
            this.afterafew = afterafew;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        private String logo_url;//银行logo

    }
}
