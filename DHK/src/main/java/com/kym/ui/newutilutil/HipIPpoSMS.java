package com.kym.ui.newutilutil;

public class HipIPpoSMS {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"code_url":"https://mch.baijinzhangfu.com/api.php/pay/upgrade?orderid=OF180705144529X0IC07&mid=3","url_type":1}
     */

    private ResultBean result;
    private DataBean data;


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class ResultBean {
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

    public static class DataBean {
        /**
         * code_url : https://mch.baijinzhangfu.com/api.php/pay/upgrade?orderid=OF180705144529X0IC07&mid=3
         * url_type : 1
         */

        private String amount;
        private String pay_url;
        private int url_type;
        private String serialNumber;
        private String details;
        private String url;
        private String orderNo;
        private String title;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getCode_url() {
            return pay_url;
        }

        public void setCode_url(String code_url) {
            this.pay_url = code_url;
        }

        public int getUrl_type() {
            return url_type;
        }

        public void setUrl_type(int url_type) {
            this.url_type = url_type;
        }
    }
}
