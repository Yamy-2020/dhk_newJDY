package com.kym.ui.info;

public class ShowTabResponse {
    private ResultInfo result;
    private ShowTabInfo data;

    public ShowTabInfo getData() {
        return data;
    }

    public void setData(ShowTabInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }


    public class ShowTabInfo {
        private String home;
        private String bill;
        private String rxf;
        private String dc;
        private String me;

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getBill() {
            return bill;
        }

        public void setBill(String bill) {
            this.bill = bill;
        }

        public String getRxf() {
            return rxf;
        }

        public void setRxf(String rxf) {
            this.rxf = rxf;
        }

        public String getDc() {
            return dc;
        }

        public void setDc(String dc) {
            this.dc = dc;
        }

        public String getMe() {
            return me;
        }

        public void setMe(String me) {
            this.me = me;
        }
    }
}
