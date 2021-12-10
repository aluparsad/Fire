package com.buns.fire.Models;

public class Membership {
    private String title, content;

    public Membership() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Membership(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
