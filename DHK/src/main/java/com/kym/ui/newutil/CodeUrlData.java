package com.kym.ui.newutil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeUrlData {

    @SerializedName("code_url")
    @Expose
    private String codeUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details;

    /**
     * 
     * @return
     *     The codeUrl
     */
    public String getCodeUrl() {
        return codeUrl;
    }

    /**
     * 
     * @param codeUrl
     *     The code_url
     */
    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

}
