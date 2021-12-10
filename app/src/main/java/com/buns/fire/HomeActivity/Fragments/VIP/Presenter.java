package com.buns.fire.HomeActivity.Fragments.VIP;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Pack;
import com.buns.fire.Models.Subscription;
import com.buns.fire.Models.User;
import com.buns.fire.Utils.Constants;
import com.buns.fire.Utils.SubscriptionCost;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;
    private int balance;
    private Subscription subscription;
    private DocumentSnapshot ds;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void buySubscription(@NonNull Pack pack) {
        getUserInfo(pack);
    }

    private void getUserInfo(@NonNull Pack pack) {
        Constants.getUser().get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.size() > 0) {
                        this.ds = queryDocumentSnapshots.getDocuments().get(0);
                        getRequiredInfo(pack);
                    }
                });
    }

    private void getRequiredInfo(@NonNull Pack pack) {
        final User u = ds.toObject(User.class);
        balance = u.getBalance();
        subscription = u.getPlan();
        processRequest(pack);
    }

    private void processRequest(Pack pack) {
        int currentPlanCost = SubscriptionCost.getInstance().getPlanCost(subscription);
        int requestedPlanCost = SubscriptionCost.getInstance().getPlanCost(pack.getTitle());
        if (balance >= (pack.getPrice() - currentPlanCost) && currentPlanCost < requestedPlanCost) {
            updateCurrentPlan(requestedPlanCost - currentPlanCost, pack.getTitle());
            mView.successfull();
        } else
            mView.error("Conditions not satisfied");
    }

    private void updateCurrentPlan(final int price, @NonNull Subscription plan) {
        ds.getReference().update(Constants.balance, FieldValue.increment(-price));
        ds.getReference().update(Constants.plan, plan);
        giveRefferedBonus(price / 5);
    }
    private void giveRefferedBonus(final int amount) {
        Constants.getRefferedUserRef()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        updateRefferedUserBalance(queryDocumentSnapshots.getDocuments().get(0).getReference(), amount);
                    }
                })
                .addOnFailureListener(e -> {
                    mView.error("bluff");
                });
    }

    private void updateRefferedUserBalance(@NonNull DocumentReference dr, int amount) {
        dr.update(Constants.balance, FieldValue.increment(amount));
    }


    @Override
    public void getPlans() {
        List<Pack> packs = new ArrayList<>();
        Pack p = new Pack();
        packs.add(new Pack(Subscription.VISITOR, 20, SubscriptionCost.getInstance().getPlanCost(Subscription.VISITOR)));
        packs.add(new Pack(Subscription.FULL_TIME_EMPLOYEE, 20, SubscriptionCost.getInstance().getPlanCost(Subscription.FULL_TIME_EMPLOYEE)));
        packs.add(new Pack(Subscription.SUPERVISOR, 20, SubscriptionCost.getInstance().getPlanCost(Subscription.SUPERVISOR)));
        packs.add(new Pack(Subscription.MANAGER, 20, SubscriptionCost.getInstance().getPlanCost(Subscription.MANAGER)));
        packs.add(new Pack(Subscription.DIRECTOR, 20, SubscriptionCost.getInstance().getPlanCost(Subscription.DIRECTOR)));
        mView.setPlans(packs);
    }
}
