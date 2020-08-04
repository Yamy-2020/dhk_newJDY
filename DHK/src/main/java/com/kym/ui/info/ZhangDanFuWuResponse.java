package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 孙孙 on 2019/3/12.
 */

public class ZhangDanFuWuResponse implements Serializable {

    private ResultInfo result;
    private List<ZhangDanFuWuResponse.ZhangDanFuWuInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<ZhangDanFuWuInfo> getData() {
        return data;
    }

    public void setData(List<ZhangDanFuWuInfo> data) {
        this.data = data;
    }

    public class ZhangDanFuWuInfo implements Serializable {
        private String tc_id;
        private String price;
        private String tc_name;
        private String tc_desc;
        private String num;
        private String original_price;

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getTc_id() {
            return tc_id;
        }

        public void setTc_id(String tc_id) {
            this.tc_id = tc_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTc_name() {
            return tc_name;
        }

        public void setTc_name(String tc_name) {
            this.tc_name = tc_name;
        }

        public String getTc_desc() {
            return tc_desc;
        }

        public void setTc_desc(String tc_desc) {
            this.tc_desc = tc_desc;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
