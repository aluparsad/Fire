package com.buns.fire.Utils;

import androidx.annotation.NonNull;

import com.buns.fire.Authentication.AuthActivity;
import com.buns.fire.BaseActivity.BaseActivity;
import com.buns.fire.HomeActivity.Activity.HomeActivity;
import com.buns.fire.Models.Subscription;
import com.buns.fire.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;
import java.util.UUID;


public class Authentication {

    private static BaseActivity bs;

    public static void setBaseActivity(BaseActivity bs) {
        Authentication.bs = bs;
    }

    public Authentication() {

    }

    public static void RegisterUser(@NonNull String uid, @NonNull String phone_num, @NonNull String name, @NonNull String refferedId) {
        DocumentReference dr = Constants.getUsersRef().document();

        User u = new User();
        u.setUserId(uid);
        u.setNumber(phone_num);
        u.setBalance(0);
        u.setName(name);
        u.setPlan(Subscription.VISITOR);
        u.setRefferedBy(refferedId);
        u.setRefferId(UUID.randomUUID().toString());
        u.setTimeAuth(Calendar.getInstance().getTimeInMillis());
        dr.set(u)
                .addOnSuccessListener(aVoid -> {
                    Constants.setUser(u);
                    bs.NavigateToActivity(HomeActivity.class, true, null);
                });
    }


    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        Constants.setUser(null);
        openAuthActivity();
    }

    public static void openAuthActivity() {
        if (bs != null) {
            bs.NavigateToActivity(AuthActivity.class, true, null);
        }
    }

}
