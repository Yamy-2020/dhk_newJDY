package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/10.
 */

public class SySMessage {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"id":"93","omid":"rd500ZbaNVcKVr8g","title":"首页","addtime":"1466753258","status":"1","content":"尊敬的用户：                                                                                                  通道更新升级，QQ通道 微信通道 支付宝通道均有下线升级，升级期间可使用快捷支付大额秒到通道，额度大到账快！伙伴们 赶紧去体验吧！！","force":"1"}]
     */

    private ResultBean result;
    private List<DataBean> data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * code : 10000
         * msg : 请求成功
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
        /**
         * id : 93
         * omid : rd500ZbaNVcKVr8g
         * title : 首页
         * addtime : 1466753258
         * status : 1
         * content : 尊敬的用户：                                                                                                  通道更新升级，QQ通道 微信通道 支付宝通道均有下线升级，升级期间可使用快捷支付大额秒到通道，额度大到账快！伙伴们 赶紧去体验吧！！
         * force : 1
         */

        private String id;
        private String omid;
        private String title;
        private String addtime;
        private String status;
        private String content;
        private String force;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOmid() {
            return omid;
        }

        public void setOmid(String omid) {
            this.omid = omid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }
    }
}
