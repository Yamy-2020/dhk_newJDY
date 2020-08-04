package com.kym.ui.newutilutil;

import com.google.gson.annotations.Expose;
import com.kym.ui.newutil.Result;

import java.util.ArrayList;
import java.util.List;

public class BonusOrder {

    @Expose
    private Result result;
    @Expose
    private List<BonusOrderDatum> data = new ArrayList<BonusOrderDatum>();

    /**
     * 
     * @return
     *     The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<BonusOrderDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<BonusOrderDatum> data) {
        this.data = data;
    }

}
