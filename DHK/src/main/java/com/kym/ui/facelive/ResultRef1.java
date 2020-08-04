package com.kym.ui.facelive;

import com.google.gson.annotations.Expose;

public class ResultRef1 {

    @Expose
    private Double confidence;
    @Expose
    private Thresholds thresholds;

    /**
     * 
     * @return
     *     The confidence
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * 
     * @param confidence
     *     The confidence
     */
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    /**
     * 
     * @return
     *     The thresholds
     */
    public Thresholds getThresholds() {
        return thresholds;
    }

    /**
     * 
     * @param thresholds
     *     The thresholds
     */
    public void setThresholds(Thresholds thresholds) {
        this.thresholds = thresholds;
    }

}
