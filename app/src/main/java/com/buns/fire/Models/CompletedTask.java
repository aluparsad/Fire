package com.buns.fire.Models;

public class CompletedTask {
    private String id;
    private String time;

    public CompletedTask() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompletedTask(String id, String time) {
        this.id = id;
        this.time = time;
    }
}
