package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/6.
 */

public class LivePhoto {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"idcard_front_url":"https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/74PMRQXT4K.jpg","idcard_back_url":"https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/HV9DVCDUZV.jpg","bank_front_url":"https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/H45O5DNDM0.jpg","idcard_hand_url":"https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/K3M9G2KC44.jpg"}
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
         * idcard_front_url : https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/74PMRQXT4K.jpg
         * idcard_back_url : https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/HV9DVCDUZV.jpg
         * bank_front_url : https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/H45O5DNDM0.jpg
         * idcard_hand_url : https://test.credit.liuxingkeji.com/var/upload/api/img/20180206/K3M9G2KC44.jpg
         */

        private String idcard_front_url;
        private String idcard_back_url;
        private String bank_front_url;
        private String idcard_hand_url;

        public String getIdcard_front_url() {
            return idcard_front_url;
        }

        public void setIdcard_front_url(String idcard_front_url) {
            this.idcard_front_url = idcard_front_url;
        }

        public String getIdcard_back_url() {
            return idcard_back_url;
        }

        public void setIdcard_back_url(String idcard_back_url) {
            this.idcard_back_url = idcard_back_url;
        }

        public String getBank_front_url() {
            return bank_front_url;
        }

        public void setBank_front_url(String bank_front_url) {
            this.bank_front_url = bank_front_url;
        }

        public String getIdcard_hand_url() {
            return idcard_hand_url;
        }

        public void setIdcard_hand_url(String idcard_hand_url) {
            this.idcard_hand_url = idcard_hand_url;
        }
    }
}
