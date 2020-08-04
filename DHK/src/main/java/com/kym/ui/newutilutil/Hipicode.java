package com.kym.ui.newutilutil;

public class Hipicode {
    /**
     * result : {"code":10000,"msg":"提交成功！"}
     * data : {"url":"https://api.hihippo.cn/order/godetail/PU1014795020817174530/c52c3df0aafd0fda47fa8541373a734af338a29e","title":"银行卡评测","details":"银行卡评测"}
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
         * msg : 提交成功！
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
         * url : https://api.hihippo.cn/order/godetail/PU1014795020817174530/c52c3df0aafd0fda47fa8541373a734af338a29e
         * title : 银行卡评测
         * details : 银行卡评测
         */

        private String url;
        private String title;
        private String details;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
