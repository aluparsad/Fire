package com.buns.fire.Models;

public class User {

    private Subscription plan;
    private int balance;
    private long timeAuth;
    private String
            refferId,
            refferedBy,
            name,
            userId,
            number;

    public long getTimeAuth() {
        return timeAuth;
    }

    public void setTimeAuth(long timeAuth) {
        this.timeAuth = timeAuth;
    }

    public String getRefferId() {
        return refferId;
    }

    public void setRefferId(String refferId) {
        this.refferId = refferId;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public User() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Subscription getPlan() {
        return plan;
    }

    public void setPlan(Subscription plan) {
        this.plan = plan;
    }

}

