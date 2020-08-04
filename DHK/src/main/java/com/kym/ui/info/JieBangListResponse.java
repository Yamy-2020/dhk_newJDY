package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class JieBangListResponse  implements Serializable {
    private ResultInfo result;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<jiebangInfo> getData() {
        return data;
    }

    public void setData(List<jiebangInfo> data) {
        this.data = data;
    }

    private List<JieBangListResponse.jiebangInfo> data;


    public class jiebangInfo implements Serializable {
        private String nickname;
        private String id;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
