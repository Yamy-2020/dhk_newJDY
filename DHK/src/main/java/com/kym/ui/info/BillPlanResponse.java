package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/8.
 */

public class BillPlanResponse implements Serializable {
    private ResultInfo result;
    private BillPlanData data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public BillPlanData getData() {
        return data;
    }

    public void setData(BillPlanData data) {
        this.data = data;
    }

    public class BillPlanData implements Serializable {
        private String is_show_button;
        private List<BillPlanInfo> bill_list;

        public String getIs_show_button() {
            return is_show_button;
        }

        public void setIs_show_button(String is_show_button) {
            this.is_show_button = is_show_button;
        }

        public List<BillPlanInfo> getBill_list() {
            return bill_list;
        }

        public void setBill_list(List<BillPlanInfo> bill_list) {
            this.bill_list = bill_list;
        }
    }

    public class BillPlanInfo implements Serializable {
        private String bill_id;
        private String status;
        private double repay_money;
        private double remain_money;
        private double repay_fee;
        private long addtime;
        private int cash_number;
        private int repay_number;
        private int summaryisfee;

        public double getSummaryisfee() {
            return summaryisfee;
        }

        public void setSummaryisfee(int summaryisfee) {
            this.summaryisfee = summaryisfee;
        }

        public int getCash_number() {
            return cash_number;
        }

        public void setCash_number(int cash_number) {
            this.cash_number = cash_number;
        }

        public int getRepay_number() {
            return repay_number;
        }

        public void setRepay_number(int repay_number) {
            this.repay_number = repay_number;
        }

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

        public double getRepay_money() {
            return repay_money;
        }

        public void setRepay_money(double repay_money) {
            this.repay_money = repay_money;
        }

        public double getRemain_money() {
            return remain_money;
        }

        public void setRemain_money(double remain_money) {
            this.remain_money = remain_money;
        }

        public double getRepay_fee() {
            return repay_fee;
        }

        public void setRepay_fee(double repay_fee) {
            this.repay_fee = repay_fee;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }
}
