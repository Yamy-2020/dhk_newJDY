package com.kym.ui.info;

import com.google.gson.annotations.Expose;
import com.kym.ui.newutil.Result;

import java.util.ArrayList;
import java.util.List;

public class TongZhi {

    @Expose
    private Result result;
    @Expose
    private List<TongZhiDatum> data = new ArrayList<TongZhiDatum>();

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
    public List<TongZhiDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<TongZhiDatum> data) {
        this.data = data;
    }

}
