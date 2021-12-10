package com.buns.fire.Models;

public class Pack {
    private Subscription title;
    private int tasks, price;

    public Pack() {
    }

    public Pack(Subscription title, int tasks, int price) {
        this.title = title;
        this.tasks = tasks;
        this.price = price;
    }

    public Subscription getTitle() {
        return title;
    }

    public void setTitle(Subscription title) {
        this.title = title;
    }

    public int getTasks() {
        return tasks;
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
