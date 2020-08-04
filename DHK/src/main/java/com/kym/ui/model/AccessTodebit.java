package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/8.
 */

public class AccessTodebit {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"bank_mobile":"15960237408","bank_no":"6227001842740299422","provincename":"福建省","cityname":"莆田市","bank_name":"中国建设银行","bank_sub":"中国建设银行股份有限公司仙游支行"}
     */

    private ResultBean result;
    private DataBean data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * bank_mobile : 15960237408
         * bank_no : 6227001842740299422
         * provincename : 福建省
         * cityname : 莆田市
         * bank_name : 中国建设银行
         * bank_sub : 中国建设银行股份有限公司仙游支行
         */

        private String bank_mobile;
        private String bank_no;
        private String provincename;
        private String cityname;
        private String bank_name;
        private String bank_sub;
        private String logo_url;

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getBank_mobile() {
            return bank_mobile;
        }

        public void setBank_mobile(String bank_mobile) {
            this.bank_mobile = bank_mobile;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getProvincename() {
            return provincename;
        }

        public void setProvincename(String provincename) {
            this.provincename = provincename;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_sub() {
            return bank_sub;
        }

        public void setBank_sub(String bank_sub) {
            this.bank_sub = bank_sub;
        }
    }
}
