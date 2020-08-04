package com.kym.ui.info;

public class XFSignResponse {
    private ResultInfo result;
    private XFSignInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public XFSignInfo getData() {
        return data;
    }

    public void setData(XFSignInfo data) {
        this.data = data;
    }

    public class XFSignInfo {
        private String orderid;
        private String smsSeq;
        private String tip;
        private String is_url;
        private String url;
        private String is_bind;

        public String getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(String is_bind) {
            this.is_bind = is_bind;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIs_url() {
            return is_url;
        }

        public void setIs_url(String is_url) {
            this.is_url = is_url;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getSmsSeq() {
            return smsSeq;
        }

        public void setSmsSeq(String smsSeq) {
            this.smsSeq = smsSeq;
        }
    }

}
