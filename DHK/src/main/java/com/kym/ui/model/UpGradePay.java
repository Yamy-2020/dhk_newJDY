package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/10.
 */

public class UpGradePay {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"pay_url":"https://www.baidu.com"}
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
         * pay_url : https://www.baidu.com
         */

        private String pay_url;

        public String getPay_url() {
            return pay_url;
        }

        public void setPay_url(String pay_url) {
            this.pay_url = pay_url;
        }
    }
}
