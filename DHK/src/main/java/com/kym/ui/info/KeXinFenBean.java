package com.kym.ui.info;

public class KeXinFenBean {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"shop":"0"}
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
         * shop : 0
         */

        private int shop;

        public int getShop() {
            return shop;
        }

        public void setShop(int shop) {
            this.shop = shop;
        }
    }
}
