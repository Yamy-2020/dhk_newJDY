package com.kym.ui.info;

import java.io.Serializable;

public class ShengQingFuWuShangResponse implements Serializable {
    private ResultInfo result;
    private NewJhDcInfo data;

    public NewJhDcInfo getData() {
        return data;
    }

    public void setData(NewJhDcInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public class NewJhDcInfo {
        private String isCanUse;
        private String isAudit;

        public String getIsCanUse() {
            return isCanUse;
        }

        public void setIsCanUse(String isCanUse) {
            this.isCanUse = isCanUse;
        }

        public String getIsAudit() {
            return isAudit;
        }

        public void setIsAudit(String isAudit) {
            this.isAudit = isAudit;
        }
    }
}