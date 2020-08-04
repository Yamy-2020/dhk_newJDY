package com.kym.ui.info;

public class HuaLuoBKResponse {
    private ResultInfo result;
    private BKInfo data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public BKInfo getData() {
        return data;
    }

    public void setData(BKInfo data) {
        this.data = data;
    }

    public class BKInfo{
        private String switchUrl;
        private String token;

        public String getSwitchUrl() {
            return switchUrl;
        }

        public void setSwitchUrl(String switchUrl) {
            this.switchUrl = switchUrl;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
