package com.buns.fire.Utils;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Subscription;

import java.util.HashMap;

public class SubscriptionCost {
    private HashMap<Subscription, Integer> subPlans;
    private static SubscriptionCost instance;

    public static SubscriptionCost getInstance() {
        if (instance == null)
            instance = new SubscriptionCost();
        return instance;
    }

    private SubscriptionCost() {
        this.subPlans = new HashMap<>();
        initPlans();
    }

    private void initPlans() {
        subPlans.put(Subscription.VISITOR, 0);
        subPlans.put(Subscription.FULL_TIME_EMPLOYEE, 1000);
        subPlans.put(Subscription.SUPERVISOR, 2000);
        subPlans.put(Subscription.MANAGER, 5000);
        subPlans.put(Subscription.DIRECTOR, 10000);
    }


    public int getTaskCost() {
        SingletonUser su = SingletonUser.getInstance();
        if (!su.getUser().getPlan().equals(Subscription.VISITOR)) {
            final int days = 20;
            int userAmount = SubscriptionCost.getInstance().getPlanCost(su.getUser().getPlan());
            userAmount -= userAmount / 20;
            final int taskAmount = userAmount / (20 * days);
            return taskAmount;
        } else {
            return 1;
        }

    }

    public int getPlanCost(@NonNull Subscription plan) {
        return subPlans.get(plan);
    }


}
