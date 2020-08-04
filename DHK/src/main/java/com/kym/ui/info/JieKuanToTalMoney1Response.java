package com.kym.ui.info;


/**
 * @author sun
 * @date 2019/11/16 2:17 PM
 */
public class JieKuanToTalMoney1Response {

    private ResultInfo result;
    private JieKuanTotalMoneyInfo data;

    public JieKuanTotalMoneyInfo getData() {
        return data;
    }

    public void setData(JieKuanTotalMoneyInfo data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public class JieKuanTotalMoneyInfo {
        private String loanAllBack;
        private String loanInterest;
        private String loanAddDate;
        private String loanrepayDate;

        public String getLoanAllBack() {
            return loanAllBack;
        }

        public void setLoanAllBack(String loanAllBack) {
            this.loanAllBack = loanAllBack;
        }

        public String getLoanInterest() {
            return loanInterest;
        }

        public void setLoanInterest(String loanInterest) {
            this.loanInterest = loanInterest;
        }

        public String getLoanAddDate() {
            return loanAddDate;
        }

        public void setLoanAddDate(String loanAddDate) {
            this.loanAddDate = loanAddDate;
        }

        public String getLoanrepayDate() {
            return loanrepayDate;
        }

        public void setLoanrepayDate(String loanrepayDate) {
            this.loanrepayDate = loanrepayDate;
        }
    }

}
