package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class BenJinTradeListResponse implements Serializable {

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
        private String amount;
        private String status;
        private String addtime;
        private String type_name;
        private String type_source_name;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_source_name() {
            return type_source_name;
        }

        public void setType_source_name(String type_source_name) {
            this.type_source_name = type_source_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
