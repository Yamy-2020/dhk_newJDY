package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class YouHuiTuiGuangResponse implements Serializable {

    private ResultInfo result;

    private List<YHTuiGuaangInfo> data;

    public List<YHTuiGuaangInfo> getData() {
        return data;
    }

    public void setData(List<YHTuiGuaangInfo> data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }



    public class YHTuiGuaangInfo implements Serializable {
        private String name;
        private String top_imgurl;
        private int totalReferrals;
        private int directPeople;
        private int indirectPeople;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTop_imgurl() {
            return top_imgurl;
        }

        public void setTop_imgurl(String top_imgurl) {
            this.top_imgurl = top_imgurl;
        }

        public int getTotalReferrals() {
            return totalReferrals;
        }

        public void setTotalReferrals(int totalReferrals) {
            this.totalReferrals = totalReferrals;
        }

        public int getDirectPeople() {
            return directPeople;
        }

        public void setDirectPeople(int directPeople) {
            this.directPeople = directPeople;
        }

        public int getIndirectPeople() {
            return indirectPeople;
        }

        public void setIndirectPeople(int indirectPeople) {
            this.indirectPeople = indirectPeople;
        }
    }
}
