package com.kym.ui.facelive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thresholds {

    @SerializedName("1e-3")
    @Expose
    private Double _1e3;
    @SerializedName("1e-5")
    @Expose
    private Double _1e5;
    @SerializedName("1e-4")
    @Expose
    private Double _1e4;
    @SerializedName("1e-6")
    @Expose
    private Double _1e6;

    /**
     * 
     * @return
     *     The _1e3
     */
    public Double get1e3() {
        return _1e3;
    }

    /**
     * 
     * @param _1e3
     *     The 1e-3
     */
    public void set1e3(Double _1e3) {
        this._1e3 = _1e3;
    }

    /**
     * 
     * @return
     *     The _1e5
     */
    public Double get1e5() {
        return _1e5;
    }

    /**
     * 
     * @param _1e5
     *     The 1e-5
     */
    public void set1e5(Double _1e5) {
        this._1e5 = _1e5;
    }

    /**
     * 
     * @return
     *     The _1e4
     */
    public Double get1e4() {
        return _1e4;
    }

    /**
     * 
     * @param _1e4
     *     The 1e-4
     */
    public void set1e4(Double _1e4) {
        this._1e4 = _1e4;
    }

    /**
     * 
     * @return
     *     The _1e6
     */
    public Double get1e6() {
        return _1e6;
    }

    /**
     * 
     * @param _1e6
     *     The 1e-6
     */
    public void set1e6(Double _1e6) {
        this._1e6 = _1e6;
    }

}
