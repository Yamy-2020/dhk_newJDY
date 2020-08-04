package com.kym.ui.info;

import java.io.Serializable;

public class HKSignResponse implements Serializable {

    private ResultInfo result;
    private HKSignInfo data;

    public HKSignInfo getData() {
        return data;
    }

    public void setData(HKSignInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public class HKSignInfo implements Serializable {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String orderid;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        private String smsSeq;

        public String getSmsSeq() {
            return smsSeq;
        }

        public void setSmsSeq(String smsSeq) {
            this.smsSeq = smsSeq;
        }

        private String is_url;

        public String getIs_url() {
            return is_url;
        }

        public void setIs_url(String is_url) {
            this.is_url = is_url;
        }

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String isMsg;

        public String getIsMsg() {
            return isMsg;
        }

        public void setIsMsg(String isMsg) {
            this.isMsg = isMsg;
        }

        private String bind_status;

        public String getBind_status() {
            return bind_status;
        }

        public void setBind_status(String bind_status) {
            this.bind_status = bind_status;
        }
        private String is_bind;

        public String getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(String is_bind) {
            this.is_bind = is_bind;
        }

        private String status;

        private String extra;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
        private String bindStatus;

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
