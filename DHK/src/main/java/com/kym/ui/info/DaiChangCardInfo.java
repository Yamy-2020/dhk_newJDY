package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class DaiChangCardInfo implements Serializable {
    private ResultInfo result;
    private List<DaiChangCardInfo.DaiChangCardData> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<DaiChangCardData> getData() {
        return data;
    }

    public void setData(List<DaiChangCardData> data) {
        this.data = data;
    }

    public class DaiChangCardData implements Serializable {
        private String amount;
        private String type;
        private String tasktime;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTasktime() {
            return tasktime;
        }

        public void setTasktime(String tasktime) {
            this.tasktime = tasktime;
        }
    }

}
