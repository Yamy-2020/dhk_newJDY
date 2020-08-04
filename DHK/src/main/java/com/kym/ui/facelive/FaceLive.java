package com.kym.ui.facelive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceLive {

    @SerializedName("time_used")
    @Expose
    private Integer timeUsed;
    @SerializedName("result_ref1")
    @Expose
    private ResultRef1 resultRef1;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    /**
     * 
     * @return
     *     The timeUsed
     */
    public Integer getTimeUsed() {
        return timeUsed;
    }

    /**
     * 
     * @param timeUsed
     *     The time_used
     */
    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    /**
     * 
     * @return
     *     The resultRef1
     */
    public ResultRef1 getResultRef1() {
        return resultRef1;
    }

    /**
     * 
     * @param resultRef1
     *     The result_ref1
     */
    public void setResultRef1(ResultRef1 resultRef1) {
        this.resultRef1 = resultRef1;
    }

    /**
     * 
     * @return
     *     The requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 
     * @param requestId
     *     The request_id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
