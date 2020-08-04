package com.kym.ui.model;

import java.util.List;

/**
 * Created by zhangph on 2018/2/9.
 */

public class UserMyMerchant {
    /**
     * result : {"code":10000,"msg":"请求成功"}
     * data : {"list":[{"lfid":"1","num1":0,"num2":0,"name":"等级1","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"2","num1":0,"num2":0,"name":"等级2","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"3","num1":0,"num2":0,"name":"等级3","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"4","num1":0,"num2":0,"name":"等级4","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}],"subDirect":0,"subIndirect":0,"subReal":0,"sum":0}
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
         * list : [{"lfid":"1","num1":0,"num2":0,"name":"等级1","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"2","num1":0,"num2":0,"name":"等级2","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"3","num1":0,"num2":0,"name":"等级3","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"},{"lfid":"4","num1":0,"num2":0,"name":"等级4","head_img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58"}]
         * subDirect : 0
         * subIndirect : 0
         * subReal : 0
         * sum : 0
         */

        private int subDirect;
        private int subIndirect;
        private int subReal;
        private int sum;
        private List<ListBean> list;


        public int getSubDirect() {
            return subDirect;
        }

        public void setSubDirect(int subDirect) {
            this.subDirect = subDirect;
        }

        public int getSubIndirect() {
            return subIndirect;
        }

        public void setSubIndirect(int subIndirect) {
            this.subIndirect = subIndirect;
        }

        public int getSubReal() {
            return subReal;
        }

        public void setSubReal(int subReal) {
            this.subReal = subReal;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * lfid : 1
             * num1 : 0
             * num2 : 0
             * name : 等级1
             * head_img : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2208082549,1989069819&fm=58
             */

            private String lfid;
            private int num1;
            private int num2;
            private String name;
            private String head_img;

            public String getLfid() {
                return lfid;
            }

            public void setLfid(String lfid) {
                this.lfid = lfid;
            }

            public int getNum1() {
                return num1;
            }

            public void setNum1(int num1) {
                this.num1 = num1;
            }

            public int getNum2() {
                return num2;
            }

            public void setNum2(int num2) {
                this.num2 = num2;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }
        }
    }
}
