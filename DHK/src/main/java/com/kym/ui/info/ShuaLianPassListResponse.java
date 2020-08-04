package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * @author sun
 * @date 2020-04-29 11:00
 */

public class ShuaLianPassListResponse implements Serializable {
    private ResultInfo result;
    private List<PassList> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<PassList> getData() {
        return data;
    }

    public void setData(List<PassList> data) {
        this.data = data;
    }

    public class PassList implements Serializable {

        /**
         *"name":"mtfacepay",
         *"single_limit":"20000.00",
         *"day_limit":"50000.00",
         *"id":"1",
         *"issign":0
         */
        private String name, single_limit, day_limit,
                id, issign;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSingle_limit() {
            return single_limit;
        }

        public void setSingle_limit(String single_limit) {
            this.single_limit = single_limit;
        }

        public String getDay_limit() {
            return day_limit;
        }

        public void setDay_limit(String day_limit) {
            this.day_limit = day_limit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIssign() {
            return issign;
        }

        public void setIssign(String issign) {
            this.issign = issign;
        }
    }
}
