package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class TuiGuangSuCaiResponse  implements Serializable {

    private static final long serialVersionUID = 2218399883247784419L;
    private ResultInfo result;
    private List<TuiGuangInfo> data;

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setData(List<TuiGuangInfo> data) {
        this.data = data;
    }

    public List<TuiGuangInfo> getData() {
        return data;
    }

    public class TuiGuangInfo implements Serializable {
        private static final long serialVersionUID = -7275276140513229786L;
        private String shareImg;
        private String thumb;
        private String name;
        private String text;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
