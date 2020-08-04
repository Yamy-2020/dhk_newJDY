package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class ShareResponse implements Serializable {
    private static final long serialVersionUID = 4300763658085505693L;
    private ResultInfo result;
    private List<ShareInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<ShareInfo> getData() {
        return data;
    }

    public void setData(List<ShareInfo> data) {
        this.data = data;
    }

    public class ShareInfo implements Serializable {

        private static final long serialVersionUID = 8509089619681753134L;
        private String title, content, about_income, share_code;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAbout_income() {
            return about_income;
        }

        public void setAbout_income(String about_income) {
            this.about_income = about_income;
        }

        public String getShare_code() {
            return share_code;
        }

        public void setShare_code(String share_code) {
            this.share_code = share_code;
        }
    }
}
