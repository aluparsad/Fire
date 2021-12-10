package com.buns.fire.Payment.Deposit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.R;
import com.google.android.material.textfield.TextInputLayout;


public class DepositDialog extends DialogFragment implements Contractor.View {

    private TextInputLayout amountTIL, transactionIdTIL;
    private Button copyBtn, doneBtn;
    private TextView title, upiId;
    private String upi;
    private Contractor.Presenter presenter;
    private LottieAnimationView loading;

    public DepositDialog(@NonNull String upi) {
        this.upi = upi;
        this.presenter = new Presenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_deposit_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitializeView(view);
        setTextViews(upi);
        setBtnClickListeners();
    }

    private void setTextViews(@NonNull String s) {
        title.setText("DEPOSIT");
        upiId.setText(s);
    }

    private void setBtnClickListeners() {
        copyBtn.setOnClickListener(v -> {
            copyUpi(upi);
            showToast("Copied");
        });                                              // Copy UPI Id to clipboard

        doneBtn.setOnClickListener(v -> {
            doneBtn.setClickable(false);
            String amount = "", transactionId = "";
            amount = amountTIL.getEditText().getText().toString();
            transactionId = transactionIdTIL.getEditText().getText().toString();

            if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(transactionId)) {
                final int amt = Math.round(Float.parseFloat(amount));
                if (amt < 1000) {
                    showToast("Insufficient amount");
                } else {
                    presenter.requestDepositTransaction(transactionId, amt);
                    ShowLoading();
                }
            } else {
                showToast("Fill Details");
            }
        });
    }

    private void InitializeView(View view) {
        loading = view.findViewById(R.id.loading);
        amountTIL = view.findViewById(R.id.amountDepostET);
        transactionIdTIL = view.findViewById(R.id.transactionIdEt);

        copyBtn = view.findViewById(R.id.copyBtn);
        doneBtn = view.findViewById(R.id.doneDeposit);

        title = view.findViewById(R.id.title);
        upiId = view.findViewById(R.id.upi);
    }

    private void copyUpi(@NonNull String upi) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("upiId", upi);
        clipboard.setPrimaryClip(clipData);
    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String errorMsg) {
        HideLoading();
        showToast(errorMsg);
        doneBtn.setClickable(true);
        getDialog().cancel();
    }

    @Override
    public void successful() {
        HideLoading();
        showToast("Processing");
        doneBtn.setClickable(true);
        getDialog().cancel();
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
