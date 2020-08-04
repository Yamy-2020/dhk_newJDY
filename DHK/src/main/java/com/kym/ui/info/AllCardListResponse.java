package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class AllCardListResponse implements Serializable {
    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<AllCardListInfo> getData() {
        return data;
    }

    public void setData(List<AllCardListInfo> data) {
        this.data = data;
    }

    private ResultInfo result;
    private java.util.List<AllCardListInfo> data;

    public class AllCardListInfo implements Serializable {
        private String logo_url;
        private String image_url;
        private String bank_name;
        private String bank_no;
        private String card_type;
        private String cardid;
        private int bills;
        private int repayment;
        private String balance;

        public int getBills() {
            return bills;
        }

        public void setBills(int bills) {
            this.bills = bills;
        }

        public int getRepayment() {
            return repayment;
        }

        public void setRepayment(int repayment) {
            this.repayment = repayment;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
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

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }
    }
}
