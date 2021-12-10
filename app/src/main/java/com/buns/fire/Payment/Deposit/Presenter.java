package com.buns.fire.Payment.Deposit;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Transaction;
import com.buns.fire.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    private Transaction.Deposit createDepositTransaction(@NonNull final String transId, final int amount) {
        Transaction.Deposit deposit = new Transaction.Deposit();
        deposit.setAmount(amount);
        deposit.setTransactionId(transId);
        deposit.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        deposit.setTime(Calendar.getInstance().getTimeInMillis());
        deposit.setStatus(Transaction.STATUS.PENDING);
        return deposit;
    }

    @Override
    public void requestDepositTransaction(String transactionId, int amt) {
        Transaction.Deposit transaction = createDepositTransaction(transactionId, amt);
        DocumentReference dr = Constants.getDeposits().document();
        transaction.setOrderId(dr.getId());

        dr
                .set(transaction)
                .addOnSuccessListener(aVoid -> {
                    mView.successful();
                })
                .addOnFailureListener(e -> {
                    mView.error("Error Try again");
                });
    }
}
