package com.kym.ui.info;

import java.util.List;

public class HKplanResponse {

    private ResultInfo result;
    private HKplanInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public HKplanInfo getData() {
        return data;
    }

    public void setData(HKplanInfo data) {
        this.data = data;
    }

    public static class HKplanInfo {

        private String settlement_number;
        private String consume_number;
        private List<ListBean> result;

        public String getSettlement_number() {
            return settlement_number;
        }

        public void setSettlement_number(String settlement_number) {
            this.settlement_number = settlement_number;
        }

        public String getConsume_number() {
            return consume_number;
        }

        public void setConsume_number(String consume_number) {
            this.consume_number = consume_number;
        }

        public List<ListBean> getResult() {
            return result;
        }

        public void setResult(List<ListBean> result) {
            this.result = result;
        }

        public static class ListBean {
            private String money;
            private String category;
            private String date;
            private String charge;
            private String totalprofit;

            public String getTotalprofit() {
                return totalprofit;
            }

            public void setTotalprofit(String totalprofit) {
                this.totalprofit = totalprofit;
            }

            private String totalfee;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCharge() {
                return charge;
            }

            public void setCharge(String charge) {
                this.charge = charge;
            }

            public String getTotalfee() {
                return totalfee;
            }

            public void setTotalfee(String totalfee) {
                this.totalfee = totalfee;
            }
        }
    }
}
