package com.kym.ui.info;

/**
 * Created by zachary on 2018/2/10.
 */

public class HomeUnconfirmResponse {
    private ResultInfo result;
    private BankListResponse.BankInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public BankListResponse.BankInfo getData() {
        return data;
    }

    public void setData(BankListResponse.BankInfo data) {
        this.data = data;
    }
}
