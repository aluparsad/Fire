package com.buns.fire.HomeActivity.Fragments.Home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Subscription;
import com.buns.fire.Utils.Constants;

public class PresenterHome implements Contractor.Presenter {
    private Contractor.View mView;

    public PresenterHome(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void copyRefferCode(@NonNull Context context) {
        if (Constants.getIsLoggedIn() && !(Constants.getCurrentUser().getPlan().equals(Subscription.VISITOR))) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText(Constants.refferId, Constants.getCurrentUser().getRefferId());
            cm.setPrimaryClip(cd);
            mView.showToast("Copied");
        } else
            mView.showToast("Upgrade Plan");
    }
}
