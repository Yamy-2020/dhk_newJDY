package com.kym.ui.info;

/**
 * @author sun
 * @date 2020-04-14 11:17
 */
public class MessageInfo {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "title='" + title + '\'' +
                '}';
    }

    public MessageInfo(String title) {
        this.title = title;
    }
}
