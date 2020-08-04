package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class GouMaiQuanYi implements Serializable {
    private static final long serialVersionUID = -3917294680571197141L;
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"current_list":{"top_imgurl":"https://app.izhisuan.com/resource/level1.png","name":"新客户","current_msg":"已推广 0 人。还差 1000 人免费升级为：老客户。"},"payment_list":[{"upgrade_level":2,"upgrade_number":"NO.101140559402","name":"老客户","payment_amount":"88.00","rate_list":{"sk":{"lf":"10%","td":"5%","zt":"5%"},"yk":{"lf":"10%","td":"5%","zt":"5%"},"hk":{"lf":"10%","td":"5%","zt":"5%"}}},{"upgrade_level":3,"upgrade_number":"NO.101140559403","name":"贵宾客户","payment_amount":"199.00","rate_list":{"sk":{"lf":"40%","td":"30%","zt":"35%"},"yk":{"lf":"40%","td":"30%","zt":"35%"},"hk":{"lf":"40%","td":"30%","zt":"35%"}}}]}
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

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -1407686646407781719L;
        /**
         * current_list : {"top_imgurl":"https://app.izhisuan.com/resource/level1.png","name":"新客户","current_msg":"已推广 0 人。还差 1000 人免费升级为：老客户。"}
         * payment_list : [{"upgrade_level":2,"upgrade_number":"NO.101140559402","name":"老客户","payment_amount":"88.00","rate_list":{"sk":{"lf":"10%","td":"5%","zt":"5%"},"yk":{"lf":"10%","td":"5%","zt":"5%"},"hk":{"lf":"10%","td":"5%","zt":"5%"}}},{"upgrade_level":3,"upgrade_number":"NO.101140559403","name":"贵宾客户","payment_amount":"199.00","rate_list":{"sk":{"lf":"40%","td":"30%","zt":"35%"},"yk":{"lf":"40%","td":"30%","zt":"35%"},"hk":{"lf":"40%","td":"30%","zt":"35%"}}}]
         */

        private CurrentListBean current_list;
        private List<PaymentListBean> payment_list;

        public CurrentListBean getCurrent_list() {
            return current_list;
        }

        public void setCurrent_list(CurrentListBean current_list) {
            this.current_list = current_list;
        }

        public List<PaymentListBean> getPayment_list() {
            return payment_list;
        }

        public void setPayment_list(List<PaymentListBean> payment_list) {
            this.payment_list = payment_list;
        }

        public static class CurrentListBean implements Serializable {
            private static final long serialVersionUID = -6230403943683928869L;
            /**
             * top_imgurl : https://app.izhisuan.com/resource/level1.png
             * name : 新客户
             * current_msg : 已推广 0 人。还差 1000 人免费升级为：老客户。
             */

            private String top_imgurl;
            private String name;
            private String current_msg;

            public String getTop_imgurl() {
                return top_imgurl;
            }

            public void setTop_imgurl(String top_imgurl) {
                this.top_imgurl = top_imgurl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCurrent_msg() {
                return current_msg;
            }

            public void setCurrent_msg(String current_msg) {
                this.current_msg = current_msg;
            }
        }

        public static class PaymentListBean implements Serializable{
            private static final long serialVersionUID = -3833965400176431858L;
            /**
             * upgrade_level : 2
             * upgrade_number : NO.101140559402
             * name : 老客户
             * payment_amount : 88.00
             * rate_list : {"sk":{"lf":"10%","td":"5%","zt":"5%"},"yk":{"lf":"10%","td":"5%","zt":"5%"},"hk":{"lf":"10%","td":"5%","zt":"5%"}}
             */

            private int upgrade_level;
            private String upgrade_number;
            private String name;
            private String payment_amount;
            private RateListBean rate_list;

            public int getUpgrade_level() {
                return upgrade_level;
            }

            public void setUpgrade_level(int upgrade_level) {
                this.upgrade_level = upgrade_level;
            }

            public String getUpgrade_number() {
                return upgrade_number;
            }

            public void setUpgrade_number(String upgrade_number) {
                this.upgrade_number = upgrade_number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPayment_amount() {
                return payment_amount;
            }

            public void setPayment_amount(String payment_amount) {
                this.payment_amount = payment_amount;
            }

            public RateListBean getRate_list() {
                return rate_list;
            }

            public void setRate_list(RateListBean rate_list) {
                this.rate_list = rate_list;
            }

            public static class RateListBean implements Serializable{
                private static final long serialVersionUID = -1311303838479913942L;
                /**
                 * sk : {"lf":"10%","td":"5%","zt":"5%"}
                 * yk : {"lf":"10%","td":"5%","zt":"5%"}
                 * hk : {"lf":"10%","td":"5%","zt":"5%"}
                 */

                private SkBean sk;
                private YkBean yk;
                private HkBean hk;

                public SkBean getSk() {
                    return sk;
                }

                public void setSk(SkBean sk) {
                    this.sk = sk;
                }

                public YkBean getYk() {
                    return yk;
                }

                public void setYk(YkBean yk) {
                    this.yk = yk;
                }

                public HkBean getHk() {
                    return hk;
                }

                public void setHk(HkBean hk) {
                    this.hk = hk;
                }

                public static class SkBean implements Serializable{
                    private static final long serialVersionUID = -8721704693010312342L;
                    /**
                     * lf : 10%
                     * td : 5%
                     * zt : 5%
                     */

                    private String lf;
                    private String td;
                    private String zt;

                    public String getLf() {
                        return lf;
                    }

                    public void setLf(String lf) {
                        this.lf = lf;
                    }

                    public String getTd() {
                        return td;
                    }

                    public void setTd(String td) {
                        this.td = td;
                    }

                    public String getZt() {
                        return zt;
                    }

                    public void setZt(String zt) {
                        this.zt = zt;
                    }
                }

                public static class YkBean implements Serializable {
                    private static final long serialVersionUID = -2374569595084559689L;
                    /**
                     * lf : 10%
                     * td : 5%
                     * zt : 5%
                     */

                    private String lf;
                    private String td;
                    private String zt;

                    public String getLf() {
                        return lf;
                    }

                    public void setLf(String lf) {
                        this.lf = lf;
                    }

                    public String getTd() {
                        return td;
                    }

                    public void setTd(String td) {
                        this.td = td;
                    }

                    public String getZt() {
                        return zt;
                    }

                    public void setZt(String zt) {
                        this.zt = zt;
                    }
                }

                public static class HkBean implements Serializable{
                    private static final long serialVersionUID = 8815367541156645848L;
                    /**
                     * lf : 10%
                     * td : 5%
                     * zt : 5%
                     */

                    private String lf;
                    private String td;
                    private String zt;

                    public String getLf() {
                        return lf;
                    }

                    public void setLf(String lf) {
                        this.lf = lf;
                    }

                    public String getTd() {
                        return td;
                    }

                    public void setTd(String td) {
                        this.td = td;
                    }

                    public String getZt() {
                        return zt;
                    }

                    public void setZt(String zt) {
                        this.zt = zt;
                    }
                }
            }
        }
    }
}
