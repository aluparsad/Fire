package com.buns.fire.Payment.Deposit;

public interface Contractor {
    interface View {
        void error(String errorMsg);
        void successful();
    }

    interface Presenter {
        void requestDepositTransaction(String transactionId, int amt);
    }
}
