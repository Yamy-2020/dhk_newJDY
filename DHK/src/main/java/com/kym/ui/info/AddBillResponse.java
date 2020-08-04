package com.kym.ui.info;

/**
 * Created by zachary on 2018/2/7.
 */

public class AddBillResponse {
    private ResultInfo result;
    private BillInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public BillInfo getData() {
        return data;
    }

    public void setData(BillInfo data) {
        this.data = data;
    }

    public class BillInfo{
        private String bill_id;

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }
    }
}
