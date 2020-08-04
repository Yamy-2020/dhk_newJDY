package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/9.
 */

public class MyChild {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"uid":"UIDZZKBP5439","name":"叶华容","mobile":"18960405826","status":"1","status_auth":"3","addtime":"1517920495"},{"uid":"UIDAY7LF8I72","name":"张鹏航","mobile":"15960237408","status":"1","status_auth":"3","addtime":"1517921254"}]
     */

    private ResultBean result;
    private List<DataBean> data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
         * uid : UIDZZKBP5439
         * name : 叶华容
         * mobile : 18960405826
         * status : 1
         * status_auth : 3
         * addtime : 1517920495
         */

        private String uid;
        private String name;
        private String mobile;
        private String status;
        private String status_auth;
        private String addtime;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_auth() {
            return status_auth;
        }

        public void setStatus_auth(String status_auth) {
            this.status_auth = status_auth;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
