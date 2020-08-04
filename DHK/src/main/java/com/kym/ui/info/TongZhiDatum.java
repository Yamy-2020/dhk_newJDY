package com.kym.ui.info;
import com.google.gson.annotations.Expose;

public class TongZhiDatum {

    @Expose
    private String id;
    @Expose
    private String mid;
    @Expose
    private String title;
    @Expose
    private String addtime;
    @Expose
    private String status;
    @Expose
    private String content;
    @Expose
    private String force;
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * 
     * @param mid
     *     The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The addtime
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     * 
     * @param addtime
     *     The addtime
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The force
     */
    public String getForce() {
        return force;
    }

    /**
     * 
     * @param force
     *     The force
     */
    public void setForce(String force) {
        this.force = force;
    }

}
