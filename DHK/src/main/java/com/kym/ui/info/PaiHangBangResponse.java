package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/7.
 */

public class PaiHangBangResponse implements Serializable{
    private ResultInfo result;

    public List<PaiHangBangInfo> getData() {
        return data;
    }

    public void setData(List<PaiHangBangInfo> data) {
        this.data = data;
    }

    private List<PaiHangBangInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }



    public class PaiHangBangInfo implements Serializable {
        private String name,grade,volume,headerimg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }
    }
}
