package com.jock.pickerview.lib;

public class bean {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"balance":39219,"cashback_remain":3000,"kade":0,"upgrade":3980,"receive":264049,"activity":-550,"clound":0,"face":0,"total_money":39219,"totalback_money":3000,"totalreceive_money":264049,"totalkade_money":0,"totalupgrade_money":3980,"totalactivity_money":-550,"totalclound_money":0,"totalface_money":0,"yesterdayMoney":151,"todayMoney":0,"yesterdaybackMoney":0,"todaybackMoney":0,"yesterdayreceiveMoney":0,"todayreceiveMoney":258,"yesterdaykadeMoney":0,"todaykadeMoney":0,"yesterdayupgradeMoney":0,"todayupgradeMoney":0,"yesterdayactivityMoney":0,"todayactivityMoney":0,"yesterdaycloundMoney":0,"todaycloundMoney":0,"yesterdayfaceMoney":0,"todayfaceMoney":0,"total_cash":52403,"totalback_cash":0,"totalreceive_cash":304418,"totalkade_cash":0,"totalupgrade_cash":4000,"totalactivity_cash":0,"totalclound_cash":0,"totalface_cash":0}
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
         * balance : 39219
         * cashback_remain : 3000
         * kade : 0
         * upgrade : 3980
         * receive : 264049
         * activity : -550
         * clound : 0
         * face : 0
         * total_money : 39219
         * totalback_money : 3000
         * totalreceive_money : 264049
         * totalkade_money : 0
         * totalupgrade_money : 3980
         * totalactivity_money : -550
         * totalclound_money : 0
         * totalface_money : 0
         * yesterdayMoney : 151
         * todayMoney : 0
         * yesterdaybackMoney : 0
         * todaybackMoney : 0
         * yesterdayreceiveMoney : 0
         * todayreceiveMoney : 258
         * yesterdaykadeMoney : 0
         * todaykadeMoney : 0
         * yesterdayupgradeMoney : 0
         * todayupgradeMoney : 0
         * yesterdayactivityMoney : 0
         * todayactivityMoney : 0
         * yesterdaycloundMoney : 0
         * todaycloundMoney : 0
         * yesterdayfaceMoney : 0
         * todayfaceMoney : 0
         * total_cash : 52403
         * totalback_cash : 0
         * totalreceive_cash : 304418
         * totalkade_cash : 0
         * totalupgrade_cash : 4000
         * totalactivity_cash : 0
         * totalclound_cash : 0
         * totalface_cash : 0
         */

        private int balance;
        private int cashback_remain;
        private int kade;
        private int upgrade;
        private int receive;
        private int activity;
        private int clound;
        private int face;
        private int total_money;
        private int totalback_money;
        private int totalreceive_money;
        private int totalkade_money;
        private int totalupgrade_money;
        private int totalactivity_money;
        private int totalclound_money;
        private int totalface_money;
        private int yesterdayMoney;
        private int todayMoney;
        private int yesterdaybackMoney;
        private int todaybackMoney;
        private int yesterdayreceiveMoney;
        private int todayreceiveMoney;
        private int yesterdaykadeMoney;
        private int todaykadeMoney;
        private int yesterdayupgradeMoney;
        private int todayupgradeMoney;
        private int yesterdayactivityMoney;
        private int todayactivityMoney;
        private int yesterdaycloundMoney;
        private int todaycloundMoney;
        private int yesterdayfaceMoney;
        private int todayfaceMoney;
        private int total_cash;
        private int totalback_cash;
        private int totalreceive_cash;
        private int totalkade_cash;
        private int totalupgrade_cash;
        private int totalactivity_cash;
        private int totalclound_cash;
        private int totalface_cash;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getCashback_remain() {
            return cashback_remain;
        }

        public void setCashback_remain(int cashback_remain) {
            this.cashback_remain = cashback_remain;
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

        public int getReceive() {
            return receive;
        }

        public void setReceive(int receive) {
            this.receive = receive;
        }

        public int getActivity() {
            return activity;
        }

        public void setActivity(int activity) {
            this.activity = activity;
        }

        public int getClound() {
            return clound;
        }

        public void setClound(int clound) {
            this.clound = clound;
        }

        public int getFace() {
            return face;
        }

        public void setFace(int face) {
            this.face = face;
        }

        public int getTotal_money() {
            return total_money;
        }

        public void setTotal_money(int total_money) {
            this.total_money = total_money;
        }

        public int getTotalback_money() {
            return totalback_money;
        }

        public void setTotalback_money(int totalback_money) {
            this.totalback_money = totalback_money;
        }

        public int getTotalreceive_money() {
            return totalreceive_money;
        }

        public void setTotalreceive_money(int totalreceive_money) {
            this.totalreceive_money = totalreceive_money;
        }

        public int getTotalkade_money() {
            return totalkade_money;
        }

        public void setTotalkade_money(int totalkade_money) {
            this.totalkade_money = totalkade_money;
        }

        public int getTotalupgrade_money() {
            return totalupgrade_money;
        }

        public void setTotalupgrade_money(int totalupgrade_money) {
            this.totalupgrade_money = totalupgrade_money;
        }

        public int getTotalactivity_money() {
            return totalactivity_money;
        }

        public void setTotalactivity_money(int totalactivity_money) {
            this.totalactivity_money = totalactivity_money;
        }

        public int getTotalclound_money() {
            return totalclound_money;
        }

        public void setTotalclound_money(int totalclound_money) {
            this.totalclound_money = totalclound_money;
        }

        public int getTotalface_money() {
            return totalface_money;
        }

        public void setTotalface_money(int totalface_money) {
            this.totalface_money = totalface_money;
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

        public int getYesterdayupgradeMoney() {
            return yesterdayupgradeMoney;
        }

        public void setYesterdayupgradeMoney(int yesterdayupgradeMoney) {
            this.yesterdayupgradeMoney = yesterdayupgradeMoney;
        }

        public int getTodayupgradeMoney() {
            return todayupgradeMoney;
        }

        public void setTodayupgradeMoney(int todayupgradeMoney) {
            this.todayupgradeMoney = todayupgradeMoney;
        }

        public int getYesterdayactivityMoney() {
            return yesterdayactivityMoney;
        }

        public void setYesterdayactivityMoney(int yesterdayactivityMoney) {
            this.yesterdayactivityMoney = yesterdayactivityMoney;
        }

        public int getTodayactivityMoney() {
            return todayactivityMoney;
        }

        public void setTodayactivityMoney(int todayactivityMoney) {
            this.todayactivityMoney = todayactivityMoney;
        }

        public int getYesterdaycloundMoney() {
            return yesterdaycloundMoney;
        }

        public void setYesterdaycloundMoney(int yesterdaycloundMoney) {
            this.yesterdaycloundMoney = yesterdaycloundMoney;
        }

        public int getTodaycloundMoney() {
            return todaycloundMoney;
        }

        public void setTodaycloundMoney(int todaycloundMoney) {
            this.todaycloundMoney = todaycloundMoney;
        }

        public int getYesterdayfaceMoney() {
            return yesterdayfaceMoney;
        }

        public void setYesterdayfaceMoney(int yesterdayfaceMoney) {
            this.yesterdayfaceMoney = yesterdayfaceMoney;
        }

        public int getTodayfaceMoney() {
            return todayfaceMoney;
        }

        public void setTodayfaceMoney(int todayfaceMoney) {
            this.todayfaceMoney = todayfaceMoney;
        }

        public int getTotal_cash() {
            return total_cash;
        }

        public void setTotal_cash(int total_cash) {
            this.total_cash = total_cash;
        }

        public int getTotalback_cash() {
            return totalback_cash;
        }

        public void setTotalback_cash(int totalback_cash) {
            this.totalback_cash = totalback_cash;
        }

        public int getTotalreceive_cash() {
            return totalreceive_cash;
        }

        public void setTotalreceive_cash(int totalreceive_cash) {
            this.totalreceive_cash = totalreceive_cash;
        }

        public int getTotalkade_cash() {
            return totalkade_cash;
        }

        public void setTotalkade_cash(int totalkade_cash) {
            this.totalkade_cash = totalkade_cash;
        }

        public int getTotalupgrade_cash() {
            return totalupgrade_cash;
        }

        public void setTotalupgrade_cash(int totalupgrade_cash) {
            this.totalupgrade_cash = totalupgrade_cash;
        }

        public int getTotalactivity_cash() {
            return totalactivity_cash;
        }

        public void setTotalactivity_cash(int totalactivity_cash) {
            this.totalactivity_cash = totalactivity_cash;
        }

        public int getTotalclound_cash() {
            return totalclound_cash;
        }

        public void setTotalclound_cash(int totalclound_cash) {
            this.totalclound_cash = totalclound_cash;
        }

        public int getTotalface_cash() {
            return totalface_cash;
        }

        public void setTotalface_cash(int totalface_cash) {
            this.totalface_cash = totalface_cash;
        }
    }
}
