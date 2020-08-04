package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/9.
 */

public class CashList {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"total_cash":"15600","cash_list":[{"id":"247886","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209153042P9I03","bank_name":"","bank_no":"","money":"5600","fee":"200","type":"1","status":"1","is_cash":"2","addtime":"1518161442","successtime":"0","extra":""},{"id":"247885","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209145505JMQ75","bank_name":"","bank_no":"","money":"5000","fee":"200","type":"1","status":"1","is_cash":"1","addtime":"1518159305","successtime":"0","extra":""},{"id":"247884","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209145325A4BL6","bank_name":"","bank_no":"","money":"5000","fee":"200","type":"1","status":"3","is_cash":"1","addtime":"1518159205","successtime":"0","extra":""}]}
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
         * total_cash : 15600
         * cash_list : [{"id":"247886","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209153042P9I03","bank_name":"","bank_no":"","money":"5600","fee":"200","type":"1","status":"1","is_cash":"2","addtime":"1518161442","successtime":"0","extra":""},{"id":"247885","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209145505JMQ75","bank_name":"","bank_no":"","money":"5000","fee":"200","type":"1","status":"1","is_cash":"1","addtime":"1518159305","successtime":"0","extra":""},{"id":"247884","omid":"rd500ZbaNVcKVr8g","uid":"UIDPHWH9H036","orderid":"CCH180209145325A4BL6","bank_name":"","bank_no":"","money":"5000","fee":"200","type":"1","status":"3","is_cash":"1","addtime":"1518159205","successtime":"0","extra":""}]
         */

        private String total_cash;
        private String totalreceive_cash;

        public String getTotalreceive_cash() {
            return totalreceive_cash;
        }

        public void setTotalreceive_cash(String totalreceive_cash) {
            this.totalreceive_cash = totalreceive_cash;
        }

        public String getTotalback_cash() {
            return totalback_cash;
        }

        public void setTotalback_cash(String totalback_cash) {
            this.totalback_cash = totalback_cash;
        }

        private String totalback_cash;
        private List<CashListBean> cash_list;

        public String getTotal_cash() {
            return total_cash;
        }

        public void setTotal_cash(String total_cash) {
            this.total_cash = total_cash;
        }

        public List<CashListBean> getCash_list() {
            return cash_list;
        }

        public void setCash_list(List<CashListBean> cash_list) {
            this.cash_list = cash_list;
        }

        public static class CashListBean {
            /**
             * id : 247886
             * omid : rd500ZbaNVcKVr8g
             * uid : UIDPHWH9H036
             * orderid : CCH180209153042P9I03
             * bank_name :
             * bank_no :
             * money : 5600
             * fee : 200
             * type : 1
             * status : 1
             * is_cash : 2
             * addtime : 1518161442
             * successtime : 0
             * extra :
             */

            private String id;
            private String omid;
            private String uid;
            private String orderid;
            private String bank_name;
            private String bank_no;
            private String money;
            private String fee;
            private String type;
            private String status;
            private String is_cash;
            private String addtime;
            private String successtime;
            private String extra;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOmid() {
                return omid;
            }

            public void setOmid(String omid) {
                this.omid = omid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_no() {
                return bank_no;
            }

            public void setBank_no(String bank_no) {
                this.bank_no = bank_no;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_cash() {
                return is_cash;
            }

            public void setIs_cash(String is_cash) {
                this.is_cash = is_cash;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getSuccesstime() {
                return successtime;
            }

            public void setSuccesstime(String successtime) {
                this.successtime = successtime;
            }

            public String getExtra() {
                return extra;
            }

            public void setExtra(String extra) {
                this.extra = extra;
            }
        }
    }
}
