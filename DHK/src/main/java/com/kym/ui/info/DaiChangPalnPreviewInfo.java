package com.kym.ui.info;


import java.util.List;

public class DaiChangPalnPreviewInfo {
    private ResultInfo result;
    private DataBean data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String number,repay_number,payment_number,userfee;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getRepay_number() {
            return repay_number;
        }

        public void setRepay_number(String repay_number) {
            this.repay_number = repay_number;
        }

        public String getPayment_number() {
            return payment_number;
        }

        public void setPayment_number(String payment_number) {
            this.payment_number = payment_number;
        }

        public String getUserfee() {
            return userfee;
        }

        public void setUserfee(String userfee) {
            this.userfee = userfee;
        }


        private List<ListBean> payconList;

        public List<ListBean> getPayconList() {
            return payconList;
        }

        public void setPayconList(List<ListBean> payconList) {
            this.payconList = payconList;
        }

        public static class ListBean {
            private String time,type,money;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
