package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/6.
 */

public class ChengShi {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"city":"呼和浩特市"},{"city":"包头市"},{"city":"乌海市"},{"city":"赤峰市"},{"city":"通辽市"},{"city":"鄂尔多斯市"},{"city":"呼伦贝尔市"},{"city":"巴彦淖尔盟"},{"city":"乌兰察布盟"},{"city":"兴安盟"},{"city":"锡林郭勒盟"},{"city":"阿拉善盟"}]
     */

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
         * city : 呼和浩特市
         */

        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
