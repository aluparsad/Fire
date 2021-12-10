package com.buns.fire.Authentication;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

public interface Contractor {
    interface View {

        void onOtpSent();

        void onSuccess();

        void onError(Exception e);

        void registeruser(FirebaseUser user);
    }

    interface Presenter {

        void loginWithPhoneNumber(Activity activity, String number);

        void VerifyOTP(String otpCode);

    }
}
