package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/6.
 */

public class BankList {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"name":"中国工商银行","code":"102100099996"},{"name":"中国农业银行","code":"103100000026"},{"name":"中国银行","code":"104100000004"},{"name":"中国建设银行","code":"105100000017"},{"name":"交通银行","code":"301290000007"},{"name":"中信银行","code":"302100011000"},{"name":"光大银行","code":"303100000006"},{"name":"华夏银行","code":"304100040000"},{"name":"中国民生银行","code":"305100000013"},{"name":"广发银行","code":"306331003281"},{"name":"招商银行","code":"308584000013"},{"name":"兴业银行","code":"309391000011"},{"name":"浦发银行","code":"310290000013"},{"name":"恒丰银行","code":"315456000105"},{"name":"浙商银行","code":"316331000018"},{"name":"徽商银行","code":"319361000013"},{"name":"上海农商银行","code":"322290000011"},{"name":"上海银行","code":"325290000012"},{"name":"中国邮政储蓄银行信用卡中心","code":"403100000004"},{"name":"平安银行","code":"307584007998"},{"name":"北京银行","code":"313100000013"},{"name":"云南省农村信用社","code":"402731057238"},{"name":"海南省农村信用社","code":"402641000014"},{"name":"广西农村信用社","code":"402611099974"},{"name":"东莞农村商业银行","code":"402602000018"},{"name":"福建省农村信用社","code":"402391000068"},{"name":"安徽省农村信用社联合社","code":"402361018886"},{"name":"浙江省农村信用社","code":"402331000007"},{"name":"江苏省农村信用社联合社","code":"402301099998"},{"name":"北京农村商业银行","code":"402100000018"},{"name":"包商银行","code":"313192000013"},{"name":"重庆银行","code":"313653000013"},{"name":"广州银行","code":"313581003284"},{"name":"昆仑银行","code":"313882000012"},{"name":"广州农村商业银行","code":"314581000011"},{"name":"浙江民泰商业银行","code":"313345400010"},{"name":"厦门银行","code":"313393080005"}]
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
         * name : 中国工商银行
         * code : 102100099996
         */

        private String name;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
