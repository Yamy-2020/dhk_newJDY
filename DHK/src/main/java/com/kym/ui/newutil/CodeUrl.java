package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;

public class CodeUrl {

    @Expose
    private Result result;
    @Expose
    private CodeUrlData data;

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
    public CodeUrlData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(CodeUrlData data) {
        this.data = data;
    }

}
