package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class TieResponse implements Serializable {

    private ResultInfo result;
    private List<TieInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<TieInfo> getData() {
        return data;
    }

    public void setData(List<TieInfo> data) {
        this.data = data;
    }

    public class TieInfo implements Serializable {
        private String balance;
        private String create_time;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
