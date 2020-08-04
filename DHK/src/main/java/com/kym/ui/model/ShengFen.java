package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/6.
 */

public class ShengFen {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"province":"其它"},{"province":"北京市"},{"province":"天津市"},{"province":"河北省"},{"province":"山西省"},{"province":"内蒙古自治区"},{"province":"辽宁省"},{"province":"吉林省"},{"province":"黑龙江省"},{"province":"上海市"},{"province":"江苏省"},{"province":"浙江省"},{"province":"安徽省"},{"province":"福建省"},{"province":"江西省"},{"province":"山东省"},{"province":"河南省"},{"province":"湖北省"},{"province":"湖南省"},{"province":"广东省"},{"province":"广西壮族自治区"},{"province":"海南省"},{"province":"重庆市"},{"province":"四川省"},{"province":"贵州省"},{"province":"云南省"},{"province":"西藏自治区"},{"province":"陕西省"},{"province":"甘肃省"},{"province":"青海省"},{"province":"宁夏回族自治区"},{"province":"新疆维吾尔自治区"}]
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
         * province : 其它
         */

        private String province;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}