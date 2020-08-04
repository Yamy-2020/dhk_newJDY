package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/6.
 */

public class ZhiHang {

    private ResultBean result;
    private List<DataBean> data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * code : 10000
         * msg : 请求成功
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
        /**
         * bank_code : 105394000015
         * bank_sub : 中国建设银行股份有限公司莆田分行（不对外）
         */

        private String bank_code;
        private String bank_sub;

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        public String getBank_sub() {
            return bank_sub;
        }

        public void setBank_sub(String bank_sub) {
            this.bank_sub = bank_sub;
        }
    }
}
