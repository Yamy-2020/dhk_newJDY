package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/10.
 */

public class More {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"id":"379","omid":"rd500ZbaNVcKVr8g","remark":"极速办卡","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170518/folder/merchant/filename/14950999769R8A9C7T9V.png.html","picurl":"https://credit.haodai.com/?ref=hd_11018512","addtime":"1488548238"},{"id":"532","omid":"rd500ZbaNVcKVr8g","remark":"极速贷款","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170518/folder/merchant/filename/14951000000YJ4ROPZFG.png.html","picurl":"https://cloud.haodai.com/Mobile/marketloan?ref=hd_11018512","addtime":"1491912246"},{"id":"488","omid":"rd500ZbaNVcKVr8g","remark":"民生办卡","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170525/folder/merchant/filename/14957013060W9EOAQD8D.png.html","picurl":"https://creditcard.cmbc.com.cn/online/mobile/index.aspx?tradeFrom=YX-QZZZSS&EnStr=5FF5A8ECCECA4595D37F079D453DAE21&amp;jg=613000003&jgEnStr=F8F6F3A67B7AE0CE597977D3749A4FF5","addtime":"1490951890"},{"id":"489","omid":"rd500ZbaNVcKVr8g","remark":"提额技巧","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170331/folder/merchant/filename/14909289882ER98Z78H1.png.html","picurl":"http://merchant.xmzzss.com/api.php/pay/wallet_card?mid=3&wallet_id=2","addtime":"1490951964"},{"id":"350","omid":"rd500ZbaNVcKVr8g","remark":"代还","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170303/folder/merchant/filename/14885316924B9YY7NVKT.png.html","picurl":"https://www.monghoo.com/#/topic/register?invite=7VF7Rr&code=0001&;hide=lf","addtime":"1488531730"},{"id":"698","omid":"rd500ZbaNVcKVr8g","remark":"信用卡","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170726/folder/merchant/filename/1501054658L55WS93O5Q.png.html","picurl":"http://ley.9meth.com/f/ley/loan?uid=bf5e4ef799cc44d8a2688b23e329e980","addtime":"1501054678"},{"id":"699","omid":"rd500ZbaNVcKVr8g","remark":"贷款超市","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170726/folder/merchant/filename/150105471707N4KE0VPT.png.html","picurl":"http://ley.9meth.com/f/ley/loan?uid=bf5e4ef799cc44d8a2688b23e329e980","addtime":"1501054745"},{"id":"700","omid":"rd500ZbaNVcKVr8g","remark":"闪贷款","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170726/folder/merchant/filename/1501054765G57Q2MRYUX.png.html","picurl":"https://openapi.haodai.com/SdTuiguang/bff?ref=hd_11018512","addtime":"1501054786"},{"id":"8","omid":"rd500ZbaNVcKVr8g","remark":"借款","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170303/folder/merchant/filename/14885316471KORD14CDM.png.html","picurl":"https://www.monghoo.com/#/topic/register?invite=7VF7Rr&code=0001&;hide=rf","addtime":"1469773906"},{"id":"423","omid":"rd500ZbaNVcKVr8g","remark":"快易花","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170324/folder/merchant/filename/14903480466KPNR57QH6.png.html","picurl":"http://t.cn/RMMPhCl","addtime":"1490348231"},{"id":"575","omid":"rd500ZbaNVcKVr8g","remark":"违章处理","pic_url":"https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170518/folder/merchant/filename/1495100000GXCQG1ID8I.png.html","picurl":"http","addtime":"1491912350"}]
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
         * id : 379
         * omid : rd500ZbaNVcKVr8g
         * remark : 极速办卡
         * pic_url : https://static.xmzzss.com/upcloud.php/upCloud/showImg/date/20170518/folder/merchant/filename/14950999769R8A9C7T9V.png.html
         * picurl : https://credit.haodai.com/?ref=hd_11018512
         * addtime : 1488548238
         */

        private String id;
        private String omid;
        private String remark;
        private String pic_url;
        private String picurl;
        private String addtime;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
