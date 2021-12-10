package com.buns.fire.HomeActivity.Fragments.Profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Subscription;
import com.buns.fire.Utils.Constants;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void refferNow(@NonNull Context context) {
        mView.ShowLoading();
        if (! (Constants.getCurrentUser().getPlan().equals(Subscription.VISITOR))) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText(Constants.refferId, Constants.getCurrentUser().getRefferId());
            cm.setPrimaryClip(cd);
            mView.showToast("Copied");
        } else
            mView.showToast("Upgrade Plan to Unlock");
        mView.HideLoading();

    }
}
