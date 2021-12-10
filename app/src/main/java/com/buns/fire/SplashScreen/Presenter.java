package com.buns.fire.SplashScreen;

import androidx.annotation.NonNull;
import com.buns.fire.Models.User;
import com.buns.fire.Utils.Constants;
import com.buns.fire.Utils.SingletonUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(@NonNull Contractor.View view) {
        this.mView = view;
    }

    private static boolean isUser = false, gotUpi = false;

    @Override
    public void Initialize() {
        getUpi();
        InitUserRef();
    }

    private void InitUserRef() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Constants.getUsersRef()
                    .whereEqualTo(Constants.userid, user.getUid())
                    .limit(1)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            return;
                        }
                        assert value != null;
                        if (value.getDocuments().size() > 0) {
                            User u = value.getDocuments().get(0).toObject(User.class);
                            Constants.setUser(u);
                            SingletonUser.getInstance();
                        }
                        isUser = true;
                        Notify();
                    });
        } else {
            isUser = true;
            Notify();
        }
    }

    private void getUpi() {
        Constants.getAdminPaymentDocRef().get().addOnSuccessListener(documentSnapshot -> {
            Constants.setPaymentUpi(documentSnapshot.getString(Constants.upi));
            gotUpi = true;
            Notify();
        });
    }




    private void Notify() {
        if (isUser && gotUpi)
            mView.navigate();
    }
}
