package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/8.
 */

public class AccountBalance {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"balance":0,"yesterdayMoney":0,"todayMoney":0}
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
         * balance : 0
         * yesterdayMoney : 0
         * todayMoney : 0
         */

        private int totalactive_money;//推广收益余额

        public void setTotalactive_money(int totalactive_money) {
            this.totalactive_money = totalactive_money;
        }

        public int getTotalactive_money() {
            return totalactive_money;
        }

        private int balance;      //账户余额
        private int total_money; // 还款账户余额
        private int yesterdayMoney;             //还款账户昨日收益
        private int todayMoney;                     //今日收益


        private int yesterdaybackMoney;     //返现账户昨日收益
        private int todaybackMoney;   //返现的今日账户
        private int cashback_remain;          //现账户余额  没有用 用的都是total的
        private int totalback_money;




        private int totalreceive_money;   //收款账户余额
        private int receive;                //收款账户余额   没有用意思一样
        private int yesterdayreceiveMoney;  //收款昨日
        private int todayreceiveMoney; //收款今日



        private int totalkade_money; //代偿账户余额
        private int kade;//代偿账户余额
        private int yesterdaykadeMoney; ;//昨日
        private int todaykadeMoney;//今日


        private int upgrade; //升级账户
        private int totalupgrade_money;  //升级账户
        private int todayupgradeMoney;//今日
        private int yesterdayupgradeMoney;//昨日



        private int totalactivity_money;  //活动账户余额
        private int activity;
        private int yesterdayactivityMoney;//昨日
        private int todayactivityMoney;//今日




        private int totalupgrade_cash;// 升级账户累计提现
        private int totalkade_cash;  //代偿累计提现
        private int totalactivity_cash;//活动账户累计提现
     /*   total_cash//还款
          totalback_cash//返现
          totalreceive_cash//收款*/




        public int getTotalactivity_cash() {
            return totalactivity_cash;
        }

        public void setTotalactivity_cash(int totalactivity_cash) {
            this.totalactivity_cash = totalactivity_cash;
        }

        public int getTodayactivityMoney() {
            return todayactivityMoney;
        }

        public void setTodayactivityMoney(int todayactivityMoney) {
            this.todayactivityMoney = todayactivityMoney;
        }

        public int getYesterdayactivityMoney() {
            return yesterdayactivityMoney;
        }

        public void setYesterdayactivityMoney(int yesterdayactivityMoney) {
            this.yesterdayactivityMoney = yesterdayactivityMoney;
        }



        public int getTotalactivity_money() {
            return totalactivity_money;
        }

        public void setTotalactivity_money(int totalactivity_money) {
            this.totalactivity_money = totalactivity_money;
        }

        public int getKade() {
            return kade;
        }

        public void setKade(int kade) {
            this.kade = kade;
        }

        public int getUpgrade() {
            return upgrade;
        }

        public void setUpgrade(int upgrade) {
            this.upgrade = upgrade;
        }

        public int getTotalupgrade_money() {
            return totalupgrade_money;
        }

        public void setTotalupgrade_money(int totalupgrade_money) {
            this.totalupgrade_money = totalupgrade_money;
        }

        public int getTodayupgradeMoney() {
            return todayupgradeMoney;
        }

        public void setTodayupgradeMoney(int todayupgradeMoney) {
            this.todayupgradeMoney = todayupgradeMoney;
        }

        public int getTotalupgrade_cash() {
            return totalupgrade_cash;
        }

        public void setTotalupgrade_cash(int totalupgrade_cash) {
            this.totalupgrade_cash = totalupgrade_cash;
        }

        public int getYesterdayupgradeMoney() {
            return yesterdayupgradeMoney;
        }

        public void setYesterdayupgradeMoney(int yesterdayupgradeMoney) {
            this.yesterdayupgradeMoney = yesterdayupgradeMoney;
        }



        public int getTotalkade_money() {
            return totalkade_money;
        }

        public void setTotalkade_money(int totalkade_money) {
            this.totalkade_money = totalkade_money;
        }

        public int getYesterdaykadeMoney() {
            return yesterdaykadeMoney;
        }

        public void setYesterdaykadeMoney(int yesterdaykadeMoney) {
            this.yesterdaykadeMoney = yesterdaykadeMoney;
        }

        public int getTodaykadeMoney() {
            return todaykadeMoney;
        }

        public void setTodaykadeMoney(int todaykadeMoney) {
            this.todaykadeMoney = todaykadeMoney;
        }

        public int getTotalkade_cash() {
            return totalkade_cash;
        }

        public void setTotalkade_cash(int totalkade_cash) {
            this.totalkade_cash = totalkade_cash;
        }

        public int getYesterdayreceiveMoney() {
            return yesterdayreceiveMoney;
        }

        public void setYesterdayreceiveMoney(int yesterdayreceiveMoney) {
            this.yesterdayreceiveMoney = yesterdayreceiveMoney;
        }

        public int getTodayreceiveMoney() {
            return todayreceiveMoney;
        }

        public void setTodayreceiveMoney(int todayreceiveMoney) {
            this.todayreceiveMoney = todayreceiveMoney;
        }



        public int getTotalreceive_money() {
            return totalreceive_money;
        }

        public void setTotalreceive_money(int totalreceive_money) {
            this.totalreceive_money = totalreceive_money;
        }

        public int getTotalback_money() {
            return totalback_money;
        }

        public void setTotalback_money(int totalback_money) {
            this.totalback_money = totalback_money;
        }



        public int getCashback_remain() {
            return cashback_remain;
        }

        public void setCashback_remain(int cashback_remain) {
            this.cashback_remain = cashback_remain;
        }

        public int getYesterdaybackMoney() {
            return yesterdaybackMoney;
        }

        public void setYesterdaybackMoney(int yesterdaybackMoney) {
            this.yesterdaybackMoney = yesterdaybackMoney;
        }

        public int getTodaybackMoney() {
            return todaybackMoney;
        }

        public void setTodaybackMoney(int todaybackMoney) {
            this.todaybackMoney = todaybackMoney;
        }

        public int getTotal_money() {
            return total_money;
        }

        public void setTotal_money(int total_money) {
            this.total_money = total_money;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getYesterdayMoney() {
            return yesterdayMoney;
        }

        public void setYesterdayMoney(int yesterdayMoney) {
            this.yesterdayMoney = yesterdayMoney;
        }

        public int getTodayMoney() {
            return todayMoney;
        }

        public void setTodayMoney(int todayMoney) {
            this.todayMoney = todayMoney;
        }
    }
}