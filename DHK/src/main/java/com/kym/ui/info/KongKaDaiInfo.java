package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class KongKaDaiInfo implements Serializable {
    private ResultInfo result;
    private List<KongKaInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<KongKaInfo> getData() {
        return data;
    }

    public void setData(List<KongKaInfo> data) {
        this.data = data;
    }

    public class KongKaInfo implements Serializable {
        private String payment_total_number; //还款笔数
        private String consumpt_total_number; //消费笔数
        private String payment_total_money; //还款金额
        private String poundage_total_fee; //手续费
        private String bank_name; //银行名称
        private String afterafew; //尾号
        private String status;
        private String logo_url;//银行logo
        private String bill_id;
        private String payment_money;
        private String consumpt_money;
        private String consumpt_number;
        private String is_show; //是否展示重置计划.0 不显示 1显示
        private String surplus_money;//未还金额
        private String surplus_day; //距离结束还款日
        private String surplus_planday;   //距离最后计划还款日天数
        private String settlmentBankCard;
        private String settlmentBankName;

        public String getSettlmentBankName() {
            return settlmentBankName;
        }

        public void setSettlmentBankName(String settlmentBankName) {
            this.settlmentBankName = settlmentBankName;
        }

        public String getSettlmentBankCard() {

            return settlmentBankCard;
        }

        public void setSettlmentBankCard(String settlmentBankCard) {
            this.settlmentBankCard = settlmentBankCard;
        }

        public String getSurplus_planday() {
            return surplus_planday;
        }

        public void setSurplus_planday(String surplus_planday) {
            this.surplus_planday = surplus_planday;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getSurplus_money() {
            return surplus_money;
        }

        public void setSurplus_money(String surplus_money) {
            this.surplus_money = surplus_money;
        }

        public String getSurplus_day() {
            return surplus_day;
        }

        public void setSurplus_day(String surplus_day) {
            this.surplus_day = surplus_day;
        }

        public String getConsumpt_number() {
            return consumpt_number;
        }

        public void setConsumpt_number(String consumpt_number) {
            this.consumpt_number = consumpt_number;
        }

        public String getConsumpt_money() {
            return consumpt_money;
        }

        public void setConsumpt_money(String consumpt_money) {
            this.consumpt_money = consumpt_money;
        }

        public String getPayment_money() {
            return payment_money;
        }

        public void setPayment_money(String payment_money) {
            this.payment_money = payment_money;
        }

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }
    }
}
