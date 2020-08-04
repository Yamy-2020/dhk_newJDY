package com.kym.ui.info;

/**
 * Created by zachary on 2018/2/5.
 */

public class LoginInfo {
    private ResultInfo result;
    private LoginChildInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public LoginChildInfo getData() {
        return data;
    }

    public void setData(LoginChildInfo data) {
        this.data = data;
    }

    public class LoginChildInfo {
        private String status;
        private int status_auth;
        private int status_perfect;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        /**
         * @return 用户当前状态 1、正常 2、冻结
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return 信息认证状态 1-未完善、2-审核中、3-已通过、4-未通过
         */
        public int getStatus_auth() {
            return status_auth;
        }

        public void setStatus_auth(int status_auth) {
            this.status_auth = status_auth;
        }

        /**
         * @return 认证步数 未认证 - 0
         */
        public int getStatus_perfect() {
            return status_perfect;
        }

        public void setStatus_perfect(int status_perfect) {
            this.status_perfect = status_perfect;
        }
    }
}
