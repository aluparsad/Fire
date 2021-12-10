package com.buns.fire.Authentication.Registration;

import android.util.Log;

import com.buns.fire.Utils.Authentication;
import com.buns.fire.Utils.Constants;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void verifyReffer(String refId, String uid, String phone_num, String name_) {
        Constants.getUsersRef()
                .limit(1)
                .whereEqualTo("refferId", refId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        Authentication.RegisterUser(uid, phone_num, name_, refId);
                    } else {
                        Authentication.RegisterUser(uid, phone_num, name_, "yogesh");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("reffer", "verifyReffer: ", e);
                });
    }

}
