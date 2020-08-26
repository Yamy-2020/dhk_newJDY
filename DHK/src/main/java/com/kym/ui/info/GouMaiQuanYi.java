package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class GouMaiQuanYi implements Serializable {

    private static final long serialVersionUID = -3917294680571197141L;
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"current_list":{"top_imgurl":"https://app.izhisuan.com/var/upload/api/img/20200805/9INGK7PVH3.jpg","level_name":"新用户（级别1）","current_msg":"已推广 5 人。"},"payment_list":[{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000","upgrade_level":2,"upgrade_level_name":"服务商（级别2）","payment_amount":"20000","upgrade_number":"NO.101140559402","rate_list":{"sk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"hk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"zs":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}}},{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000","upgrade_level":4,"upgrade_level_name":"金牌服务商（级别4）","payment_amount":"40000","upgrade_number":"NO.101140559404","rate_list":{"sk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"hk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"zs":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}}}]}
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

    public static class ResultBean  implements Serializable{
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

    public static class DataBean  implements Serializable{
        private static final long serialVersionUID = -1407686646407781719L;
        /**
         * current_list : {"top_imgurl":"https://app.izhisuan.com/var/upload/api/img/20200805/9INGK7PVH3.jpg","level_name":"新用户（级别1）","current_msg":"已推广 5 人。"}
         * payment_list : [{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000","upgrade_level":2,"upgrade_level_name":"服务商（级别2）","payment_amount":"20000","upgrade_number":"NO.101140559402","rate_list":{"sk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"hk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"zs":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}}},{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000","upgrade_level":4,"upgrade_level_name":"金牌服务商（级别4）","payment_amount":"40000","upgrade_number":"NO.101140559404","rate_list":{"sk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"hk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"zs":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}}}]
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

        public static class CurrentListBean  implements Serializable{
            private static final long serialVersionUID = 1298038504797059016L;
            /**
             * top_imgurl : https://app.izhisuan.com/var/upload/api/img/20200805/9INGK7PVH3.jpg
             * level_name : 新用户（级别1）
             * current_msg : 已推广 5 人。
             */

            private String top_imgurl;
            private String level_name;
            private String current_msg;

            public String getTop_imgurl() {
                return top_imgurl;
            }

            public void setTop_imgurl(String top_imgurl) {
                this.top_imgurl = top_imgurl;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }

            public String getCurrent_msg() {
                return current_msg;
            }

            public void setCurrent_msg(String current_msg) {
                this.current_msg = current_msg;
            }
        }

        public static class PaymentListBean implements Serializable{
            private static final long serialVersionUID = 9118586858751769122L;
            /**
             * profit_ratio : 0.0000
             * profit_ratio_zt : 0.0000
             * profit_ratio_jt : 0.0000
             * upgrade_level : 2
             * upgrade_level_name : 服务商（级别2）
             * payment_amount : 20000
             * upgrade_number : NO.101140559402
             * rate_list : {"sk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"hk":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"},"zs":{"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}}
             */

            private double profit_ratio;
            private double profit_ratio_zt;
            private double profit_ratio_jt;
            private int upgrade_level;
            private String upgrade_level_name;
            private String payment_amount;
            private String upgrade_number;
            private RateListBean rate_list;

            public double getProfit_ratio() {
                return profit_ratio;
            }

            public void setProfit_ratio(double profit_ratio) {
                this.profit_ratio = profit_ratio;
            }

            public double getProfit_ratio_zt() {
                return profit_ratio_zt;
            }

            public void setProfit_ratio_zt(double profit_ratio_zt) {
                this.profit_ratio_zt = profit_ratio_zt;
            }

            public double getProfit_ratio_jt() {
                return profit_ratio_jt;
            }

            public void setProfit_ratio_jt(double profit_ratio_jt) {
                this.profit_ratio_jt = profit_ratio_jt;
            }

            public int getUpgrade_level() {
                return upgrade_level;
            }

            public void setUpgrade_level(int upgrade_level) {
                this.upgrade_level = upgrade_level;
            }

            public String getUpgrade_level_name() {
                return upgrade_level_name;
            }

            public void setUpgrade_level_name(String upgrade_level_name) {
                this.upgrade_level_name = upgrade_level_name;
            }

            public String getPayment_amount() {
                return payment_amount;
            }

            public void setPayment_amount(String payment_amount) {
                this.payment_amount = payment_amount;
            }

            public String getUpgrade_number() {
                return upgrade_number;
            }

            public void setUpgrade_number(String upgrade_number) {
                this.upgrade_number = upgrade_number;
            }

            public RateListBean getRate_list() {
                return rate_list;
            }

            public void setRate_list(RateListBean rate_list) {
                this.rate_list = rate_list;
            }

            public static class RateListBean  implements Serializable{
                private static final long serialVersionUID = -7771157518964881555L;
                /**
                 * sk : {"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}
                 * hk : {"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}
                 * zs : {"profit_ratio":"0.0000","profit_ratio_zt":"0.0000","profit_ratio_jt":"0.0000"}
                 */

                private SkBean sk;
                private HkBean hk;
                private ZsBean zs;

                public SkBean getSk() {
                    return sk;
                }

                public void setSk(SkBean sk) {
                    this.sk = sk;
                }

                public HkBean getHk() {
                    return hk;
                }

                public void setHk(HkBean hk) {
                    this.hk = hk;
                }

                public ZsBean getZs() {
                    return zs;
                }

                public void setZs(ZsBean zs) {
                    this.zs = zs;
                }

                public static class SkBean implements Serializable{
                    private static final long serialVersionUID = -3117225609502090783L;
                    /**
                     * profit_ratio : 0.0000
                     * profit_ratio_zt : 0.0000
                     * profit_ratio_jt : 0.0000
                     */

                    private double profit_ratio;
                    private double profit_ratio_zt;
                    private double profit_ratio_jt;

                    public double getProfit_ratio() {
                        return profit_ratio;
                    }

                    public void setProfit_ratio(double profit_ratio) {
                        this.profit_ratio = profit_ratio;
                    }

                    public double getProfit_ratio_zt() {
                        return profit_ratio_zt;
                    }

                    public void setProfit_ratio_zt(double profit_ratio_zt) {
                        this.profit_ratio_zt = profit_ratio_zt;
                    }

                    public double getProfit_ratio_jt() {
                        return profit_ratio_jt;
                    }

                    public void setProfit_ratio_jt(double profit_ratio_jt) {
                        this.profit_ratio_jt = profit_ratio_jt;
                    }
                }

                public static class HkBean  implements Serializable{
                    private static final long serialVersionUID = -787117922234180030L;
                    /**
                     * profit_ratio : 0.0000
                     * profit_ratio_zt : 0.0000
                     * profit_ratio_jt : 0.0000
                     */

                    private double profit_ratio;
                    private double profit_ratio_zt;
                    private double profit_ratio_jt;

                    public double getProfit_ratio() {
                        return profit_ratio;
                    }

                    public void setProfit_ratio(double profit_ratio) {
                        this.profit_ratio = profit_ratio;
                    }

                    public double getProfit_ratio_zt() {
                        return profit_ratio_zt;
                    }

                    public void setProfit_ratio_zt(double profit_ratio_zt) {
                        this.profit_ratio_zt = profit_ratio_zt;
                    }

                    public double getProfit_ratio_jt() {
                        return profit_ratio_jt;
                    }

                    public void setProfit_ratio_jt(double profit_ratio_jt) {
                        this.profit_ratio_jt = profit_ratio_jt;
                    }
                }

                public static class ZsBean  implements Serializable{
                    private static final long serialVersionUID = -3178569891983558684L;
                    /**
                     * profit_ratio : 0.0000
                     * profit_ratio_zt : 0.0000
                     * profit_ratio_jt : 0.0000
                     */

                    private double profit_ratio;
                    private double profit_ratio_zt;
                    private double profit_ratio_jt;

                    public double getProfit_ratio() {
                        return profit_ratio;
                    }

                    public void setProfit_ratio(double profit_ratio) {
                        this.profit_ratio = profit_ratio;
                    }

                    public double getProfit_ratio_zt() {
                        return profit_ratio_zt;
                    }

                    public void setProfit_ratio_zt(double profit_ratio_zt) {
                        this.profit_ratio_zt = profit_ratio_zt;
                    }

                    public double getProfit_ratio_jt() {
                        return profit_ratio_jt;
                    }

                    public void setProfit_ratio_jt(double profit_ratio_jt) {
                        this.profit_ratio_jt = profit_ratio_jt;
                    }
                }
            }
        }
    }
}
