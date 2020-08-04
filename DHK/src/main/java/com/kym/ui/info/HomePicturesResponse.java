package com.kym.ui.info;

import java.util.List;

/**
 * Created by zachary on 2018/2/10.
 */

public class HomePicturesResponse {

    private ResultInfo result;
    private List<HomePicture> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<HomePicture> getData() {
        return data;
    }

    public void setData(List<HomePicture> data) {
        this.data = data;
    }

    public class HomePicture {
        private String img_url;
        private String jump_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }
}
