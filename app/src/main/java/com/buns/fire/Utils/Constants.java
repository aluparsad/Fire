package com.buns.fire.Utils;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Subscription;
import com.buns.fire.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constants {

    private static String paymentUpi;
    private static User user;
    public static final String
            adUrl = "https://firebasestorage.googleapis.com/v0/b/moneygram-835ff.appspot.com/o/ad.png?alt=media&token=d5310c0c-717b-40ce-8a61-af0c6f670381",
            helpSupportUrl = "https://t.me/moneygramfam/",
            vipAdUrl = "https://firebasestorage.googleapis.com/v0/b/moneygram-835ff.appspot.com/o/VIP.png?alt=media&token=79361ade-12e8-4686-8d19-10f011320d5c",
            plan = "plan",
            time = "time",
            id = "id",
            balance = "balance",
            upi = "UPI",
            userid = "userId",
            Users = "USERS",
            withdrawal = "WITHDRAWAL",
            deposits = "DEPOSITS",
            tasks = "TASKS",
            refferId = "refferId",
            admin = "ADMIN";

    private static FirebaseFirestore instance = FirebaseFirestore.getInstance();

    public static String getPaymentUpi() {
        return paymentUpi;
    }

    public static void setPaymentUpi(@NonNull String paymentUpi) {
        Constants.paymentUpi = paymentUpi;
    }


    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static Boolean getIsLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null && getCurrentUser() != null;
    }

    public static Query getUser() {
        return instance
                .collection(Users)
                .whereEqualTo(userid, FirebaseAuth.getInstance().getCurrentUser().getUid())
                .limit(1);
    }

    public static User getCurrentUser() {
        return user;
    }

    public static boolean setUser(User u) {
        user = u;
        return true;
    }

    public static CollectionReference getUsersRef() {
        return instance.collection(Constants.Users);
    }

    public static CollectionReference getAdminCollRef() {
        return FirebaseFirestore.getInstance().collection(admin);
    }

    public static DocumentReference getAdminPaymentDocRef() {
        return getAdminCollRef().document(admin);
    }

    public static CollectionReference getDeposits() {
        return getAdminPaymentDocRef().collection(deposits);
    }

    public static CollectionReference getWithdrawal() {
        return getAdminPaymentDocRef().collection(withdrawal);
    }

    public static Task<QuerySnapshot> getRefferedUserRef() {
        return getUsersRef().limit(1).whereEqualTo(refferId, getCurrentUser().getRefferedBy()).get();
    }

    public static CollectionReference getTasksRef(@NonNull Subscription subscription, @NonNull String currentDate) {
        return FirebaseFirestore.getInstance().collection(tasks).document(currentDate).collection(subscription.toString());
    }

}
