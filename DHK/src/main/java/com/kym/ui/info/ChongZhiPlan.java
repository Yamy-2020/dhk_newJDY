package com.kym.ui.info;

/**
 * Created by zachary on 2018/2/5.
 */

public class ChongZhiPlan {
    private ResultInfo result;
    private ChongZhiInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public ChongZhiInfo getData() {
        return data;
    }

    public void setData(ChongZhiInfo data) {
        this.data = data;
    }

    public class ChongZhiInfo {
        private String cardid;
        private String money;
        private String type;
        private String area;
        private String mode;
        private String cardsurplus;
        private String number;
        private String date;
        private String reset_bill_id;

        public String getReset_bill_id() {
            return reset_bill_id;
        }

        public void setReset_bill_id(String reset_bill_id) {
            this.reset_bill_id = reset_bill_id;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getCardsurplus() {
            return cardsurplus;
        }

        public void setCardsurplus(String cardsurplus) {
            this.cardsurplus = cardsurplus;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
