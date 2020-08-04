package com.kym.ui.info;

import java.io.Serializable;
import java.util.List;

public class CourseResponse implements Serializable {

    private ResultInfo result;
    private List<CourseInfo> data;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<CourseInfo> getData() {
        return data;
    }

    public void setData(List<CourseInfo> data) {
        this.data = data;
    }

    public class CourseInfo implements Serializable {

        private String subject;
        private String answer;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
