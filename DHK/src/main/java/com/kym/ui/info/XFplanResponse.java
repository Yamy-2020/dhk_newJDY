package com.kym.ui.info;

import java.util.List;

public class XFplanResponse {

    private ResultInfo result;
    private XFplanInfo data;

    public XFplanInfo getData() {
        return data;
    }

    public void setData(XFplanInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }


    public static class XFplanInfo {
        private String settlement_number;
        private String consume_number;

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

        private List<ListBean> result;

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
            private String fee;
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

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
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
