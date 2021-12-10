package com.buns.fire.Payment.Withdraw;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.Models.Transaction;
import com.buns.fire.R;
import com.google.android.material.textfield.TextInputLayout;

public class WithdrawDialog extends DialogFragment implements Contractor.View {

    private EditText upiIdET, amountET;
    private LottieAnimationView loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_withdraw_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeView(view, "WITHDRAW");
        setButtonClickListeners(view);
    }

    private void setButtonClickListeners(@NonNull View view) {
        Button requestBtn = view.findViewById(R.id.requestWithdrawBtn);

        requestBtn.setOnClickListener(v -> {
            String amount = "", upi = "";

            amount = amountET.getText().toString();
            upi = upiIdET.getText().toString();

            if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(upi)) {
                final int amt = Math.round(Float.parseFloat(amount));
                if (amt < 1000) {
                    ShowToast("At Least Rs.1000");
                } else {
                    ShowLoading();
                    new Presenter(this).requestWithdrawalTransaction(createWithdrawalTransaction(amt, upi));
                }
            } else {
                ShowToast("Fill Details");
            }
        });

    }

    private void InitializeView(@NonNull View view, @NonNull String title) {
        loading = view.findViewById(R.id.loading);
        ((TextView) view.findViewById(R.id.titleWT)).setText(title);
        upiIdET = ((TextInputLayout) view.findViewById(R.id.upiWithdrawET)).getEditText();
        amountET = ((TextInputLayout) view.findViewById(R.id.amountWithdrawET)).getEditText();
    }

    private void ShowToast(@NonNull String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private Transaction.Withdrawal createWithdrawalTransaction(int amt, @NonNull final String upiId) {
        Transaction.Withdrawal transaction = new Transaction.Withdrawal();
        transaction.setAmount(amt);
        transaction.setUpiId(upiId);
        return transaction;
    }


    @Override
    public void error(@NonNull String msg) {
        HideLoading();
        ShowToast(msg);
        dismiss();
    }

    @Override
    public void successfull() {
        HideLoading();
        ShowToast("Processing");
        dismiss();
    }

    private void ShowLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }

    private void HideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }

}
