package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class GuiZuQuanYi implements Serializable {
    private ResultInfo result;
    private List<GuiZuQuanYiInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<GuiZuQuanYiInfo> getData() {
        return data;
    }

    public void setData(List<GuiZuQuanYiInfo> data) {
        this.data = data;
    }

    public class GuiZuQuanYiInfo implements  Serializable {
        private String url;
        private String text;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
