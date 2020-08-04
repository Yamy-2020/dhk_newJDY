package com.kym.ui.info;

import java.util.List;

/**
 * Created by zachary on 2018/2/6.
 */

public class BankNameResponse {
    private ResultInfo result;
    private List<BankNameInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<BankNameInfo> getData() {
        return data;
    }

    public void setData(List<BankNameInfo> data) {
        this.data = data;
    }

    public class BankNameInfo {
        private String name;
        private String code;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
