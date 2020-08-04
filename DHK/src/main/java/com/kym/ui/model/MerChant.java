package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/9.
 */

public class MerChant {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"tel":"18888888888","wx_connection":"","qq_connection":"111111111"}
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
         * tel : 18888888888
         * wx_connection :
         * qq_connection : 111111111
         */

        private String tel;
        private String wx_connection;
        private String qq_connection;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getWx_connection() {
            return wx_connection;
        }

        public void setWx_connection(String wx_connection) {
            this.wx_connection = wx_connection;
        }

        public String getQq_connection() {
            return qq_connection;
        }

        public void setQq_connection(String qq_connection) {
            this.qq_connection = qq_connection;
        }
    }
}
