package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/9.
 */

public class TiXianInFo {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"name":"等级1","rate":"0.0038","lfid":"1","fee":"200","transactionfee":"100","cash_min":"5000","msg":"提现时间","logo":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}
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
         * name : 等级1
         * rate : 0.0038
         * lfid : 1
         * fee : 200
         * transactionfee : 100
         * cash_min : 5000
         * msg : 提现时间
         * logo : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58
         */

        private String name;
        private String rate;
        private String lfid;
        private String fee;
        private String transactionfee;
        private String cash_min;
        private String msg;
        private String logo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getLfid() {
            return lfid;
        }

        public void setLfid(String lfid) {
            this.lfid = lfid;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getTransactionfee() {
            return transactionfee;
        }

        public void setTransactionfee(String transactionfee) {
            this.transactionfee = transactionfee;
        }

        public String getCash_min() {
            return cash_min;
        }

        public void setCash_min(String cash_min) {
            this.cash_min = cash_min;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
