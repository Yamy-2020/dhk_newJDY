package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class PayListResponse implements Serializable {

    private ResultInfo result;
    private List<ZDInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<ZDInfo> getData() {
        return data;
    }

    public void setData(List<ZDInfo> data) {
        this.data = data;
    }

    public class ZDInfo implements Serializable {
        private String upfee;
        private String status;
        private String addtime;
        private String stoptime;


        public String getUpfee() {
            return upfee;
        }

        public void setUpfee(String upfee) {
            this.upfee = upfee;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStoptime() {
            return stoptime;
        }

        public void setStoptime(String stoptime) {
            this.stoptime = stoptime;
        }
    }
}
