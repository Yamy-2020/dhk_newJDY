package com.kym.ui.newutil;

public class OpenWeb {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"openurl":"https://test.credit.liuxingkeji.com/var/html/OACFAF7RUSRKwechat.html"}
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
         * openurl : https://test.credit.liuxingkeji.com/var/html/OACFAF7RUSRKwechat.html
         */

        private String openurl;

        public String getOpenurl() {
            return openurl;
        }

        public void setOpenurl(String openurl) {
            this.openurl = openurl;
        }
    }
}
