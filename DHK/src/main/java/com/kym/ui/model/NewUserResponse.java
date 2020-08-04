package com.kym.ui.model;

/**
 * Created by zhangph on 2018/2/7.
 */

public class NewUserResponse {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"uid":"UIDAY7LF8I72","name":"张鹏航","mobile":"15960237408","idcard":"350322199103277113","headimage":"","referrername":"张奎","referrermobile":"15606093672","balance":"","sharelink":"https://test.credit.liuxingkeji.com//api.php/user/regwap/omid/rd500ZbaNVcKVr8g/puid/UIDAY7LF8I72"}
     */

    private ResultBean result;
    private DataBean data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * uid : UIDAY7LF8I72
         * name : 张鹏航
         * mobile : 15960237408
         * idcard : 350322199103277113
         * headimage :
         * referrername : 张奎
         * referrermobile : 15606093672
         * balance :
         * sharelink : https://test.credit.liuxingkeji.com//api.php/user/regwap/omid/rd500ZbaNVcKVr8g/puid/UIDAY7LF8I72
         */

        private String uid;
        private String name;
        private String mobile;
        private String idcard;
        private String headimage;
        private String referrername;
        private String referrermobile;
        private String balance;
        private String sharelink;
        private String sharetitle;
        private String shareremark;
        private String showlink;
        private String handlecard_url;
        private String theloan_url;
        private String level;
        private String share_bgimg;
        private String rec;
        private String con;
        private String rep_big;
        private String rep_small;
        private String adimg;
        private String is_yuyue;
        private String yuyue_url;
        private String yuyue_title;
        private String yuyue_introduce;
        private String is_ad;
        private String ad_title;
        private String kade_small;
        private String kade_big;
        private String yx_fee;

        public String getYx_fee() {
            return yx_fee;
        }

        public void setYx_fee(String yx_fee) {
            this.yx_fee = yx_fee;
        }

        public String getKade_small() {
            return kade_small;
        }

        public void setKade_small(String kade_small) {
            this.kade_small = kade_small;
        }

        public String getKade_big() {
            return kade_big;
        }

        public void setKade_big(String kade_big) {
            this.kade_big = kade_big;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getIs_ad() {
            return is_ad;
        }

        public void setIs_ad(String is_ad) {
            this.is_ad = is_ad;
        }

        private String ad_url;
        private String ad_to_url;

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public String getAd_to_url() {
            return ad_to_url;
        }

        public void setAd_to_url(String ad_to_url) {
            this.ad_to_url = ad_to_url;
        }

        public String getYuyue_title() {
            return yuyue_title;
        }

        public void setYuyue_title(String yuyue_title) {
            this.yuyue_title = yuyue_title;
        }

        public String getYuyue_introduce() {
            return yuyue_introduce;
        }

        public void setYuyue_introduce(String yuyue_introduce) {
            this.yuyue_introduce = yuyue_introduce;
        }

        public String getIs_yuyue() {
            return is_yuyue;
        }

        public void setIs_yuyue(String is_yuyue) {
            this.is_yuyue = is_yuyue;
        }

        public String getYuyue_url() {
            return yuyue_url;
        }

        public void setYuyue_url(String yuyue_url) {
            this.yuyue_url = yuyue_url;
        }

        public String getAdimg() {
            return adimg;
        }

        public void setAdimg(String adimg) {
            this.adimg = adimg;
        }

        public String getRec() {
            return rec;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public String getRep_big() {
            return rep_big;
        }

        public void setRep_big(String rep_big) {
            this.rep_big = rep_big;
        }

        public String getRep_small() {
            return rep_small;
        }

        public void setRep_small(String rep_small) {
            this.rep_small = rep_small;
        }

        public String getSafe_mobile() {
            return safe_mobile;
        }

        public void setSafe_mobile(String safe_mobile) {
            this.safe_mobile = safe_mobile;
        }

        private String banka;
        private String safe_mobile;

        public String getBanka() {
            return banka;
        }

        public void setBanka(String banka) {
            this.banka = banka;
        }

        public String getShare_bgimg() {
            return share_bgimg;
        }

        public void setShare_bgimg(String share_bgimg) {
            this.share_bgimg = share_bgimg;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSharetitle() {
            return sharetitle;
        }

        public void setSharetitle(String sharetitle) {
            this.sharetitle = sharetitle;
        }

        public String getShareremark() {
            return shareremark;
        }

        public void setShareremark(String shareremark) {
            this.shareremark = shareremark;
        }

        public String getShowlink() {
            return showlink;
        }

        public void setShowlink(String showlink) {
            this.showlink = showlink;
        }

        public String getHandlecard_url() {
            return handlecard_url;
        }

        public void setHandlecard_url(String handlecard_url) {
            this.handlecard_url = handlecard_url;
        }

        public String getTheloan_url() {
            return theloan_url;
        }

        public void setTheloan_url(String theloan_url) {
            this.theloan_url = theloan_url;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public String getReferrername() {
            return referrername;
        }

        public void setReferrername(String referrername) {
            this.referrername = referrername;
        }

        public String getReferrermobile() {
            return referrermobile;
        }

        public void setReferrermobile(String referrermobile) {
            this.referrermobile = referrermobile;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getSharelink() {
            return sharelink;
        }

        public void setSharelink(String sharelink) {
            this.sharelink = sharelink;
        }
    }
}
