package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class AllQianYueListResponse implements Serializable {
    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<AllQianYueListInfo> getData() {
        return data;
    }

    public void setData(List<AllQianYueListInfo> data) {
        this.data = data;
    }

    private ResultInfo result;
    private List<AllQianYueListInfo> data;

    public class AllQianYueListInfo implements Serializable {
        private String bank_no;
        private String bank_name;
        private List<Channellist> channellist;

        public List<Channellist> getChannellist() {
            return channellist;
        }

        public void setChannellist(List<Channellist> channellist) {
            this.channellist = channellist;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }


        public class Channellist implements Serializable {
            private String nickname;
            private String type;
            private String is_sign;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIs_sign() {
                return is_sign;
            }

            public void setIs_sign(String is_sign) {
                this.is_sign = is_sign;
            }
        }
    }
}
