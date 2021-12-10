package com.buns.fire.Payment.Withdraw;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Transaction;

public interface Contractor {
    interface View{
        void error(@NonNull String msg);
        void successfull();
    }
    interface Presenter{
        void requestWithdrawalTransaction(@NonNull Transaction.Withdrawal transaction);
    }
}
