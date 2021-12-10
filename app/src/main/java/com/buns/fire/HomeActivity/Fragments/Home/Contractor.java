package com.buns.fire.HomeActivity.Fragments.Home;

import android.content.Context;

import androidx.annotation.NonNull;

public interface Contractor {
    interface View{
        void showToast(@NonNull String msg);
    }
    interface Presenter{
        void copyRefferCode(@NonNull Context context);
    }
}
