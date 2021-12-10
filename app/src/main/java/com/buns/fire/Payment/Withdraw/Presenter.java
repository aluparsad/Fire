package com.buns.fire.Payment.Withdraw;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Transaction;
import com.buns.fire.Utils.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.util.Calendar;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestWithdrawalTransaction(@NonNull Transaction.Withdrawal transaction) {
        Constants.getUser().get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    processTransaction(queryDocumentSnapshots.getDocuments().get(0).getReference(), transaction);
                })
                .addOnFailureListener(e -> {
                    mView.error("Error");
                });
    }

    private void processTransaction(@NonNull DocumentReference dr, Transaction.Withdrawal transaction) {
        dr.get().addOnSuccessListener(documentSnapshot -> {
            int balance = 0;
            balance = Math.round(Float.parseFloat(documentSnapshot.get(Constants.balance).toString()));

            if (balance >= transaction.getAmount()) {
                createTransaction(transaction);
            } else
                mView.error("Insufficient Balance");
        });
    }

    private void createTransaction(Transaction.Withdrawal transaction) {
        DocumentReference dr = Constants.getWithdrawal().document();
        transaction.setOrderId(dr.getId());
        transaction.setTime(Calendar.getInstance().getTimeInMillis());
        transaction.setUid(Constants.getCurrentUser().getUserId());
        transaction.setStatus(Transaction.STATUS.PENDING);

        dr
                .set(transaction)
                .addOnSuccessListener(aVoid -> {
                    updateBalance(transaction.getAmount());
                });
    }

    private void updateBalance(int amount) {
        Constants.getUser().get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    queryDocumentSnapshots.getDocuments().get(0)
                            .getReference()
                            .update(Constants.balance, FieldValue.increment(-amount))
                            .addOnSuccessListener(aVoid -> {
                                mView.successfull();
                            })
                            .addOnFailureListener(e -> {
                                mView.error("Error Incrementing");
                            });
                })
                .addOnFailureListener(e -> {
                    mView.error("balance Update Error");
                });
    }
}
