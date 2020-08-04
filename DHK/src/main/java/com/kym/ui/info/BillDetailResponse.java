package com.kym.ui.info;

import java.util.List;

/**
 * Created by zachary on 2018/2/8.
 */

public class BillDetailResponse {
    private ResultInfo result;
    private BillDetailInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public BillDetailInfo getData() {
        return data;
    }

    public void setData(BillDetailInfo data) {
        this.data = data;
    }

    public class BillDetailInfo {
        private String cardid;
        private int repay_number;
        private String repay_days;
        private double repay_money;
        private double remain_money;
        private double repay_fee;
        private double summaryisfee;
        private String status;
        private long addtime;
        private String paystatus;
        private List<BillPlanInfo> plan;

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public double getSummaryisfee() {
            return summaryisfee;
        }

        public void setSummaryisfee(double summaryisfee) {
            this.summaryisfee = summaryisfee;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public int getRepay_number() {
            return repay_number;
        }

        public void setRepay_number(int repay_number) {
            this.repay_number = repay_number;
        }

        public String getRepay_days() {
            return repay_days;
        }

        public void setRepay_days(String repay_days) {
            this.repay_days = repay_days;
        }

        /**
         * @return 还款总金额
         */
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

        /**
         * @return 还款总服务费
         */
        public double getRepay_fee() {
            return repay_fee;
        }

        public void setRepay_fee(double repay_fee) {
            this.repay_fee = repay_fee;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public List<BillPlanInfo> getPlan() {
            return plan;
        }

        public void setPlan(List<BillPlanInfo> plan) {
            this.plan = plan;
        }
    }

    public class BillPlanInfo {
        private double play_money;
        private String status;
        private long addtime;
        private double repay_fee;
        private String paystatus;

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public double getRepay_fee() {
            return repay_fee;
        }

        public void setRepay_fee(double repay_fee) {
            this.repay_fee = repay_fee;
        }

        public double getPlay_money() {
            return play_money;
        }

        public void setPlay_money(double play_money) {
            this.play_money = play_money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }
}
