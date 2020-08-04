package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zachary on 2018/2/7.
 */

public class VideoResponse implements Serializable {
    private ResultInfo result;
    private List<VideoInfo> data;

    public List<VideoInfo> getData() {
        return data;
    }

    public void setData(List<VideoInfo> data) {
        this.data = data;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }


    public class VideoInfo implements  Serializable{
        private String img;
        private String url;
        private String title;
        private String desc;
        private String see_times;
        private String zan_times;
        private String share_times;
        private String id;
        private String is_agree;

        public String getIs_agree() {
            return is_agree;
        }

        public void setIs_agree(String is_agree) {
            this.is_agree = is_agree;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSee_times() {
            return see_times;
        }

        public void setSee_times(String see_times) {
            this.see_times = see_times;
        }

        public String getZan_times() {
            return zan_times;
        }

        public void setZan_times(String zan_times) {
            this.zan_times = zan_times;
        }

        public String getShare_times() {
            return share_times;
        }

        public void setShare_times(String share_times) {
            this.share_times = share_times;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
