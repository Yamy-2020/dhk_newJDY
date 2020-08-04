package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class CardSignResponse implements Serializable {
    private ResultInfo result;
    private List<CardSignInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<CardSignResponse.CardSignInfo> getData() {
        return data;
    }

    public void setData(List<CardSignResponse.CardSignInfo> data) {
        this.data = data;
    }

    public class CardSignInfo implements Serializable {
        private String id;
        private String nickname;
        private String isEntry;

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

        public String getIsEntry() {
            return isEntry;
        }

        public void setIsEntry(String isEntry) {
            this.isEntry = isEntry;
        }
    }
}
