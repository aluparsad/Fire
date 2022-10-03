package com.buns.fire.HomeActivity.Fragments.Profile;

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
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.Models.Subscription;
import com.buns.fire.Payment.Deposit.DepositDialog;
import com.buns.fire.Payment.Withdraw.WithdrawDialog;
import com.buns.fire.R;
import com.buns.fire.Utils.Authentication;
import com.buns.fire.Utils.Constants;


public class ProfileFragment extends Fragment implements Contractor.View {

    private Contractor.Presenter presenter;
    private Button logout, deposit, withdraw, inviteNow;
    private TextView name, number, balance, num_tasks, subscription_plan;
    private LottieAnimationView loading;

    public ProfileFragment() {
        if (!Constants.getIsLoggedIn()) {
            Authentication.openAuthActivity();
        }
        presenter = new Presenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitializeView(view);
        setBtnClickListeners();
    }

    private void setBtnClickListeners() {
        logout.setOnClickListener(v -> Authentication.logout());

        deposit.setOnClickListener(v -> new DepositDialog(Constants.getPaymentUpi()).show(getActivity().getSupportFragmentManager(), "DEPOSIT"));
        withdraw.setOnClickListener(v -> new WithdrawDialog().show(getActivity().getSupportFragmentManager(), null));

        inviteNow.setOnClickListener(v -> {
            presenter.refferNow(getContext());
        });
    }

    private void setUserDetails(String... profileDetailString) {
        name.setText(profileDetailString[0]);
        number.setText(profileDetailString[1]);
    }

    private void setAccountDetails(String... accountDetails) {
        balance.setText(accountDetails[0]);
        subscription_plan.setText(accountDetails[1]);
        final String plan = Constants.getCurrentUser().getPlan().toString();

//        if (TextUtils.equals(Subscription.VISITOR.toString(), plan))
//            num_tasks.setText("20");
//        else
//            num_tasks.setText("20");
        num_tasks.setText("20");
    }

    private void InitializeView(@NonNull View v) {
        loading = v.findViewById(R.id.loading);
        logout = v.findViewById(R.id.btn_logout);
        deposit = v.findViewById(R.id.btn_deposit);
        withdraw = v.findViewById(R.id.btn_withdraw);
        inviteNow = v.findViewById(R.id.inviteBtn);

        name = v.findViewById(R.id.name_profile_tv);
        number = v.findViewById(R.id.num_profile_tv);

        balance = v.findViewById(R.id.balanceTV);
        num_tasks = v.findViewById(R.id.tasksTv);
        subscription_plan = v.findViewById(R.id.subsTv);
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowLoading();
        updateData();
        HideLoading();
    }


    @Override
    public void showToast(@NonNull final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }

    @Override
    public void HideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }

    private void updateData() {
        setUserDetails(Constants.getCurrentUser().getName(), Constants.getCurrentUser().getNumber());
        setAccountDetails("" + Constants.getCurrentUser().getBalance(), Constants.getCurrentUser().getPlan().toString());
    }

}