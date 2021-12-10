package com.buns.fire.HomeActivity.Activity;

import androidx.annotation.NonNull;

import java.text.ParseException;

public interface HomeContractor {
    interface View {
        void ShowLoading();

        void HideLoading();

        void isEligible(@NonNull Boolean status);
    }

    interface Presenter {
        void getIsEligible() throws ParseException;
    }
}
