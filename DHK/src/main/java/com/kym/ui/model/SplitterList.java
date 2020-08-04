package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/9.
 */

public class SplitterList {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"splitter":"1241","type":"1","addtime":"1518096735","typename":"代还分润","f_name":"叶华容","f_mobile":"189****5826","f_level_name":"等级1","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}]
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
         * splitter : 1241
         * type : 1
         * addtime : 1518096735
         * typename : 代还分润
         * f_name : 叶华容
         * f_mobile : 189****5826
         * f_level_name : 等级1
         * head_img : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58
         */

        private String splitter;
        private String type;
        private String addtime;
        private String typename;
        private String f_name;
        private String f_mobile;
        private String f_level_name;
        private String head_img;
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSplitter() {
            return splitter;
        }

        public void setSplitter(String splitter) {
            this.splitter = splitter;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getF_mobile() {
            return f_mobile;
        }

        public void setF_mobile(String f_mobile) {
            this.f_mobile = f_mobile;
        }

        public String getF_level_name() {
            return f_level_name;
        }

        public void setF_level_name(String f_level_name) {
            this.f_level_name = f_level_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
