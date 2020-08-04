package com.kym.ui.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WebViewBean implements Serializable {
    private static final long serialVersionUID = -5127707589235258870L;
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"KYT-OSVC-Token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJwaG9uZVwiOlwiMTM3MTc3NjY3NTdcIixcInVzZXJOYW1lXCI6XCIxMzcxNzc2Njc1N1wiLFwidXNlcklkXCI6XCI4NGRmMWMwZTEzNzE3NzY2NzU3XCJ9IiwiaXNzIjoieHVleWluZ0BhaW5pbi5jb20iLCJleHAiOjE1OTc4MDc4ODEsImlhdCI6MTU5NTIxNTg4MSwiYXV0aF9zY29wZXMiOiJhdXRoX3Njb3Blc19hY2Nlc3NfdG9rZW4ifQ.n1nBmFq6oHvKz8PP7LQGslwo8Rivud83r_0qjkkn5XQ","url":"http://47.92.35.45:18025/v1/agent/login/checktoken?version=v1&timestamp=20200720113121&agentCode=0081&sign=865a80554851dc39d1c76e42a635c540"}
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

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = -1555570077085069717L;
        /**
         * KYT-OSVC-Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJwaG9uZVwiOlwiMTM3MTc3NjY3NTdcIixcInVzZXJOYW1lXCI6XCIxMzcxNzc2Njc1N1wiLFwidXNlcklkXCI6XCI4NGRmMWMwZTEzNzE3NzY2NzU3XCJ9IiwiaXNzIjoieHVleWluZ0BhaW5pbi5jb20iLCJleHAiOjE1OTc4MDc4ODEsImlhdCI6MTU5NTIxNTg4MSwiYXV0aF9zY29wZXMiOiJhdXRoX3Njb3Blc19hY2Nlc3NfdG9rZW4ifQ.n1nBmFq6oHvKz8PP7LQGslwo8Rivud83r_0qjkkn5XQ
         * url : http://47.92.35.45:18025/v1/agent/login/checktoken?version=v1&timestamp=20200720113121&agentCode=0081&sign=865a80554851dc39d1c76e42a635c540
         */

        @SerializedName("KYT-OSVC-Token")
        private String KYTOSVCToken;
        private String url;

        public String getKYTOSVCToken() {
            return KYTOSVCToken;
        }

        public void setKYTOSVCToken(String KYTOSVCToken) {
            this.KYTOSVCToken = KYTOSVCToken;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
