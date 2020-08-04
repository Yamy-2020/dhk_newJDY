package com.kym.ui.info;

import java.util.List;

/**
 * Created by zachary on 2018/2/10.
 */

public class HomeConfirmResponse {
    private ResultInfo result;
    private List<BankListResponse.BankInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<BankListResponse.BankInfo> getData() {
        return data;
    }

    public void setData(List<BankListResponse.BankInfo> data) {
        this.data = data;
    }
}
