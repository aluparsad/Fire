package com.buns.fire.Authentication;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.buns.fire.Models.User;
import com.buns.fire.Utils.Constants;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Presenter implements Contractor.Presenter {
    private final Contractor.View mView;
    private final String TAG = "authactivity";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }


    private void loginWithCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance()
                .signInWithCredential(phoneAuthCredential)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "loginWithCredentials: " + authResult.getUser());
                    userExists(Objects.requireNonNull(authResult.getUser()));

                })
                .addOnFailureListener(mView::onError);
    }

    private void userExists(FirebaseUser user) {
        FirebaseFirestore.getInstance()
                .collection("USERS")
                .whereEqualTo("number", user.getPhoneNumber())
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && queryDocumentSnapshots.getDocuments().size() > 0) {
                        User u = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        Constants.setUser(u);
                        mView.onSuccess();
                    } else {
                        mView.registeruser(user);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Listen failed", e);
                });
    }


    @Override
    public void loginWithPhoneNumber(Activity activity, String number) {
        PhoneAuthOptions options = new PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
                .setActivity(activity)
                .setPhoneNumber("+91" + number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callback)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    public void VerifyOTP(String otpCode) {
        Log.d(TAG, "loginWithOtp: ");
        if (!TextUtils.isEmpty(mVerificationId)) {
            PhoneAuthCredential cred = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
            loginWithCredentials(cred);
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
            mView.onOtpSent();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());
            loginWithCredentials(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: " + e.getMessage());
            mView.onError(e);
        }
    };

}
