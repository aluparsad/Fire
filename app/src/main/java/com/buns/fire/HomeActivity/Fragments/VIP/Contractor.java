package com.buns.fire.HomeActivity.Fragments.VIP;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Pack;

import java.util.List;

public interface Contractor {
    interface View {
        void error(@NonNull String msg);
        void setPlans(@NonNull List<Pack> plans);
        void showLoading();
        void hideLoading();

        void successfull();
    }

    interface Presenter {

        void buySubscription(@NonNull Pack pack);

        void getPlans();
    }
}
