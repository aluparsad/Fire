package com.buns.fire.HomeActivity.Fragments.Profile;

import android.content.Context;

import androidx.annotation.NonNull;

public interface Contractor {
    interface View {
        void showToast(@NonNull String msg);

        void ShowLoading();

        void HideLoading();
    }

    interface Presenter {
        void refferNow(@NonNull Context context);
    }
}
