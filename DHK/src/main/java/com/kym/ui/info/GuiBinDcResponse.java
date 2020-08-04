package com.kym.ui.info;

import java.io.Serializable;

public class GuiBinDcResponse implements Serializable {
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
        private String fee;
        private String sheng;
        private String due;
        private String levelName;

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getSheng() {
            return sheng;
        }

        public void setSheng(String sheng) {
            this.sheng = sheng;
        }

        public String getDue() {
            return due;
        }

        public void setDue(String due) {
            this.due = due;
        }
    }
}