package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/8.
 */

public class UserUpGrade {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"fee_clerk_manager":"8800","fee_manager_boss":"16800","fee_clerk_boss":"21800","level":[{"id":"1","omid":"rd500ZbaNVcKVr8g","name":"等级1","level":"1","rate":"0.0044","transactionfee":"100","fee":"200","cash_min":"5000","num":"3","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"2","omid":"rd500ZbaNVcKVr8g","name":"等级2","level":"2","rate":"0.0043","transactionfee":"100","fee":"200","cash_min":"5000","num":"13","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"3","omid":"rd500ZbaNVcKVr8g","name":"等级3","level":"3","rate":"0.0042","transactionfee":"100","fee":"200","cash_min":"5000","num":"1","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"4","omid":"rd500ZbaNVcKVr8g","name":"等级4","level":"4","rate":"0.0041","transactionfee":"100","fee":"200","cash_min":"5000","num":"1","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}],"userLevel":{"id":"1","omid":"rd500ZbaNVcKVr8g","name":"等级1","level":"1","rate":"0.0044","transactionfee":"100","fee":"200","cash_min":"5000","num":"3","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},"recommended":3,"recommended1":10}
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
         * fee_clerk_manager : 8800
         * fee_manager_boss : 16800
         * fee_clerk_boss : 21800
         * level : [{"id":"1","omid":"rd500ZbaNVcKVr8g","name":"等级1","level":"1","rate":"0.0044","transactionfee":"100","fee":"200","cash_min":"5000","num":"3","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"2","omid":"rd500ZbaNVcKVr8g","name":"等级2","level":"2","rate":"0.0043","transactionfee":"100","fee":"200","cash_min":"5000","num":"13","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"3","omid":"rd500ZbaNVcKVr8g","name":"等级3","level":"3","rate":"0.0042","transactionfee":"100","fee":"200","cash_min":"5000","num":"1","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"id":"4","omid":"rd500ZbaNVcKVr8g","name":"等级4","level":"4","rate":"0.0041","transactionfee":"100","fee":"200","cash_min":"5000","num":"1","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}]
         * userLevel : {"id":"1","omid":"rd500ZbaNVcKVr8g","name":"等级1","level":"1","rate":"0.0044","transactionfee":"100","fee":"200","cash_min":"5000","num":"3","top_imgurl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}
         * recommended : 3
         * recommended1 : 10
         */

        private String fee_clerk_manager;
        private String fee_manager_boss;
        private String fee_clerk_boss;
        private String fee_super_vip;

        public String getFee_super_vip() {
            return fee_super_vip;
        }

        public void setFee_super_vip(String fee_super_vip) {
            this.fee_super_vip = fee_super_vip;
        }

        private UserLevelBean userLevel;
        private int recommended;
        private int recommended1;
        private List<LevelBean> level;

        public String getFee_clerk_manager() {
            return fee_clerk_manager;
        }

        public void setFee_clerk_manager(String fee_clerk_manager) {
            this.fee_clerk_manager = fee_clerk_manager;
        }

        public String getFee_manager_boss() {
            return fee_manager_boss;
        }

        public void setFee_manager_boss(String fee_manager_boss) {
            this.fee_manager_boss = fee_manager_boss;
        }

        public String getFee_clerk_boss() {
            return fee_clerk_boss;
        }

        public void setFee_clerk_boss(String fee_clerk_boss) {
            this.fee_clerk_boss = fee_clerk_boss;
        }

        public UserLevelBean getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(UserLevelBean userLevel) {
            this.userLevel = userLevel;
        }

        public int getRecommended() {
            return recommended;
        }

        public void setRecommended(int recommended) {
            this.recommended = recommended;
        }

        public int getRecommended1() {
            return recommended1;
        }

        public void setRecommended1(int recommended1) {
            this.recommended1 = recommended1;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public static class UserLevelBean {
            /**
             * id : 1
             * omid : rd500ZbaNVcKVr8g
             * name : 等级1
             * level : 1
             * rate : 0.0044
             * transactionfee : 100
             * fee : 200
             * cash_min : 5000
             * num : 3
             * top_imgurl : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58
             */

            private String id;
            private String omid;
            private String name;
            private String level;
            private String rate;
            private String transactionfee;
            private String fee;
            private String cash_min;
            private String num;
            private String top_imgurl;

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

            public String getTransactionfee() {
                return transactionfee;
            }

            public void setTransactionfee(String transactionfee) {
                this.transactionfee = transactionfee;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getCash_min() {
                return cash_min;
            }

            public void setCash_min(String cash_min) {
                this.cash_min = cash_min;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getTop_imgurl() {
                return top_imgurl;
            }

            public void setTop_imgurl(String top_imgurl) {
                this.top_imgurl = top_imgurl;
            }
        }

        public static class LevelBean {
            /**
             * id : 1
             * omid : rd500ZbaNVcKVr8g
             * name : 等级1
             * level : 1
             * rate : 0.0044
             * transactionfee : 100
             * fee : 200
             * cash_min : 5000
             * num : 3
             * top_imgurl : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58
             */

            private String id;
            private String omid;
            private String name;
            private String level;
            private String rate;
            private String transactionfee;
            private String fee;
            private String cash_min;
            private String num;
            private String top_imgurl;

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

            public String getTransactionfee() {
                return transactionfee;
            }

            public void setTransactionfee(String transactionfee) {
                this.transactionfee = transactionfee;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getCash_min() {
                return cash_min;
            }

            public void setCash_min(String cash_min) {
                this.cash_min = cash_min;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getTop_imgurl() {
                return top_imgurl;
            }

            public void setTop_imgurl(String top_imgurl) {
                this.top_imgurl = top_imgurl;
            }
        }
    }
}