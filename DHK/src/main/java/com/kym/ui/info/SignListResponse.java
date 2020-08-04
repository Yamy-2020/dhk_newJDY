package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class SignListResponse implements Serializable {
    private ResultInfo result;
    private List<SignListResponse.SignInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<SignListResponse.SignInfo> getData() {
        return data;
    }

    public void setData(List<SignListResponse.SignInfo> data) {
        this.data = data;
    }

    public class SignInfo implements Serializable {
        private String id;
        private String nickname;
        private String is_contract;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIs_contract() {
            return is_contract;
        }

        public void setIs_contract(String is_contract) {
            this.is_contract = is_contract;
        }
    }
}
