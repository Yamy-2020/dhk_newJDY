package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/9.
 */

public class SplitterTotalTime {

    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : [{"type":1,"name":"代还分润","img":"https://test.credit.liuxingkeji.com/static/images/api/ic_single.png","splitter":4104},{"type":2,"name":"升级分润","img":"https://test.credit.liuxingkeji.com/static/images/api/ic_single.png","splitter":0}]
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
         * type : 1
         * name : 代还分润
         * img : https://test.credit.liuxingkeji.com/static/images/api/ic_single.png
         * splitter : 4104
         */

        private int type;
        private String name;
        private String img;
        private int splitter;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSplitter() {
            return splitter;
        }

        public void setSplitter(int splitter) {
            this.splitter = splitter;
        }
    }
}
