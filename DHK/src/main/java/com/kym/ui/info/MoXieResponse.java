package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;


public class MoXieResponse implements Serializable {

    private ResultInfo result;
    private List<MoXieResponse.moxieInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<MoXieResponse.moxieInfo> getData() {
        return data;
    }

    public void setData(List<moxieInfo> data) {
        this.data = data;
    }

    public static class moxieInfo implements Serializable {
        private String bank_name;
        private String card_num;
        private String name_on_card;
        private String current_bill_amt;
        private String current_bill_paid_amt;
        private String current_bill_remain_amt;
        private String bill_date;
        private String payment_due_date;
        private String task_info;
        private String bg_img;
        private String logo_img;
        private String bill_type;
        private String credit_limit;
        private String show_bill_img;
        private String bill_way;
        private String bank_code;

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        private String updatetime;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        private String createtime;



        public String getPaysource() {
            return paysource;
        }

        public void setPaysource(String paysource) {
            this.paysource = paysource;
        }

        private String paysource;

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        public String getBill_way() {
            return bill_way;
        }

        public void setBill_way(String bill_way) {
            this.bill_way = bill_way;
        }

        public String getShow_bill_img() {
            return show_bill_img;
        }

        public void setShow_bill_img(String show_bill_img) {
            this.show_bill_img = show_bill_img;
        }

        public String getCredit_limit() {
            return credit_limit;
        }

        public void setCredit_limit(String credit_limit) {
            this.credit_limit = credit_limit;
        }

        public String getBill_type() {
            return bill_type;
        }

        public void setBill_type(String bill_type) {
            this.bill_type = bill_type;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCard_num() {
            return card_num;
        }

        public void setCard_num(String card_num) {
            this.card_num = card_num;
        }

        public String getName_on_card() {
            return name_on_card;
        }

        public void setName_on_card(String name_on_card) {
            this.name_on_card = name_on_card;
        }

        public String getCurrent_bill_amt() {
            return current_bill_amt;
        }

        public void setCurrent_bill_amt(String current_bill_amt) {
            this.current_bill_amt = current_bill_amt;
        }

        public String getCurrent_bill_paid_amt() {
            return current_bill_paid_amt;
        }

        public void setCurrent_bill_paid_amt(String current_bill_paid_amt) {
            this.current_bill_paid_amt = current_bill_paid_amt;
        }

        public String getCurrent_bill_remain_amt() {
            return current_bill_remain_amt;
        }

        public void setCurrent_bill_remain_amt(String current_bill_remain_amt) {
            this.current_bill_remain_amt = current_bill_remain_amt;
        }

        public String getBill_date() {
            return bill_date;
        }

        public void setBill_date(String bill_date) {
            this.bill_date = bill_date;
        }

        public String getPayment_due_date() {
            return payment_due_date;
        }

        public void setPayment_due_date(String payment_due_date) {
            this.payment_due_date = payment_due_date;
        }

        public String getTask_info() {
            return task_info;
        }

        public void setTask_info(String task_info) {
            this.task_info = task_info;
        }

        public String getBg_img() {
            return bg_img;
        }

        public void setBg_img(String bg_img) {
            this.bg_img = bg_img;
        }

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }
    }
}
