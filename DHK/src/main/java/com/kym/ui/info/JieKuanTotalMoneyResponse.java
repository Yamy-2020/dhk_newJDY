package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class JieKuanTotalMoneyResponse implements Serializable {
    private ResultInfo result;
    private List<JieKuanTotalMoneyInfo> data;

    public List<JieKuanTotalMoneyInfo> getData() {
        return data;
    }

    public void setData(List<JieKuanTotalMoneyInfo> data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public class JieKuanTotalMoneyInfo implements Serializable{
        private String hk_money;
        private String jk_money;
        private String hk_time;
        private String jk_time;
        private String jk_date;
        private String rixi;
        private String status;

        private String is_overdue;

        public String getIs_overdue() {
            return is_overdue;
        }

        public void setIs_overdue(String is_overdue) {
            this.is_overdue = is_overdue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getHk_money() {
            return hk_money;
        }

        public void setHk_money(String hk_money) {
            this.hk_money = hk_money;
        }

        public String getJk_money() {
            return jk_money;
        }

        public void setJk_money(String jk_money) {
            this.jk_money = jk_money;
        }

        public String getHk_time() {
            return hk_time;
        }

        public void setHk_time(String hk_time) {
            this.hk_time = hk_time;
        }

        public String getJk_time() {
            return jk_time;
        }

        public void setJk_time(String jk_time) {
            this.jk_time = jk_time;
        }

        public String getJk_date() {
            return jk_date;
        }

        public void setJk_date(String jk_date) {
            this.jk_date = jk_date;
        }

        public String getRixi() {
            return rixi;
        }

        public void setRixi(String rixi) {
            this.rixi = rixi;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private List<TotalMoneyInfo> loanBackDetail;

        public List<TotalMoneyInfo> getLoanBackDetail() {
            return loanBackDetail;
        }

        public void setLoanBackDetail(List<TotalMoneyInfo> loanBackDetail) {
            this.loanBackDetail = loanBackDetail;
        }

        public class TotalMoneyInfo implements Serializable {

            private String date;
            private String money;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}