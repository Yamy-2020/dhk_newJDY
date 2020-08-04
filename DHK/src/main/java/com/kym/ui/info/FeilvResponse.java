package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/6.
 */

public class FeilvResponse implements Serializable {
    private ResultInfo result;
    private List<FeiLvInfo> data;

    public List<FeiLvInfo> getData() {
        return data;
    }

    public void setData(List<FeiLvInfo> data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }


    public class FeiLvInfo implements Serializable {

        private String name;
        private String level;
        private String rate;
        private String fee;
        private String return_ratio;
        private String direct_ratio;
        private String profits_ratio;

        public String getReturn_ratio() {
            return return_ratio;
        }

        public void setReturn_ratio(String return_ratio) {
            this.return_ratio = return_ratio;
        }

        public String getDirect_ratio() {
            return direct_ratio;
        }

        public void setDirect_ratio(String direct_ratio) {
            this.direct_ratio = direct_ratio;
        }

        public String getProfits_ratio() {
            return profits_ratio;
        }

        public void setProfits_ratio(String profits_ratio) {
            this.profits_ratio = profits_ratio;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

    }
}
