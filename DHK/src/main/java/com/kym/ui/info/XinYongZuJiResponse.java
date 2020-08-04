package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class XinYongZuJiResponse implements Serializable {

    private ResultInfo result;
    private List<XinYongZuJiInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<XinYongZuJiInfo> getData() {
        return data;
    }

    public void setData(List<XinYongZuJiInfo> data) {
        this.data = data;
    }

    public class XinYongZuJiInfo implements Serializable {
        private String score;
        private String createtime;
        private String type_name;
        private String desc;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
