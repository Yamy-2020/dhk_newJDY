package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class NewDetail {

    @Expose
    private Result result;
    @Expose
    private List<NewDetailDatum> data = new ArrayList<NewDetailDatum>();

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
    public List<NewDetailDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<NewDetailDatum> data) {
        this.data = data;
    }

}
