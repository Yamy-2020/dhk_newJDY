package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;

public class HandleCard {

    @Expose
    private Result result;
    @Expose
    private HandleCardData data;

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
    public HandleCardData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(HandleCardData data) {
        this.data = data;
    }

}
