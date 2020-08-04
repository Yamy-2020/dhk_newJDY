package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/6.
 */

public class SaoMaPassListResponse implements Serializable {
    private ResultInfo result;
    private List<PassList> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<PassList> getData() {
        return data;
    }

    public void setData(List<PassList> data) {
        this.data = data;
    }

    public class PassList implements Serializable {

        /**
         * "name":"云闪付",
         * "single_limit":"20000.00",
         * "day_limit":"50000.00",
         * "id":"1",
         * "issign":0,
         * "is_use":1,
         * "y_log":"https://credit.liuxingkeji.com/static/images/clound/y_ysf.png",
         * "n_log":"https://credit.liuxingkeji.com/static/images/clound/n_ysf.png",
         * "c_log":"https://credit.liuxingkeji.com/static/images/clound/c_ysf.png",
         * "tip":"单笔限额20000元,单日限额50000,手续费交易金额1000以下0.6%+1,1000以上0.6%+1",
         * "bank_name":"云闪付和各大主流银行APP"
         */
        private String name, single_limit, day_limit,
                id, issign, is_use, y_log, n_log, c_log, tip, bank_name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSingle_limit() {
            return single_limit;
        }

        public void setSingle_limit(String single_limit) {
            this.single_limit = single_limit;
        }

        public String getDay_limit() {
            return day_limit;
        }

        public void setDay_limit(String day_limit) {
            this.day_limit = day_limit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIssign() {
            return issign;
        }

        public void setIssign(String issign) {
            this.issign = issign;
        }

        public String getIs_use() {
            return is_use;
        }

        public void setIs_use(String is_use) {
            this.is_use = is_use;
        }

        public String getY_log() {
            return y_log;
        }

        public void setY_log(String y_log) {
            this.y_log = y_log;
        }

        public String getN_log() {
            return n_log;
        }

        public void setN_log(String n_log) {
            this.n_log = n_log;
        }

        public String getC_log() {
            return c_log;
        }

        public void setC_log(String c_log) {
            this.c_log = c_log;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }
    }
}
