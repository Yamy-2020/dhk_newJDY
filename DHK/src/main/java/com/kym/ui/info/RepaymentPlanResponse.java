package com.kym.ui.info;

import java.util.List;

/**
 * Created by zachary on 2018/2/7.
 */

public class RepaymentPlanResponse {
    private ResultInfo result;
    private List<RepaymentPlanInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<RepaymentPlanInfo> getData() {
        return data;
    }

    public void setData(List<RepaymentPlanInfo> data) {
        this.data = data;
    }

    public class RepaymentPlanInfo{
        private String time;
        private double money;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }
}
