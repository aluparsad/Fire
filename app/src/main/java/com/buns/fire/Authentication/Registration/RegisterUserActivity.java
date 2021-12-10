package com.buns.fire.Authentication.Registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.buns.fire.Authentication.AuthActivity;
import com.buns.fire.BaseActivity.BaseActivity;
import com.buns.fire.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterUserActivity extends BaseActivity implements Contractor.View {

    private TextInputLayout name, refferedId;
    private Button submitBtn, loginBtn;
    private Contractor.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        String uid, phone_num;
        uid = getIntent().getStringExtra("uid");
        phone_num = getIntent().getStringExtra("phone_num");
        presenter = new Presenter(this);
        InitializeView();
        setButtonClickListeners(uid, phone_num);

    }

    private void setButtonClickListeners(String uid, String phone_num) {
        submitBtn.setOnClickListener(v -> {
            String
                    name_ = name.getEditText().getText().toString(),
                    refId = refferedId.getEditText().getText().toString();

            if (!(TextUtils.isEmpty(name_) || TextUtils.isEmpty(refId))) {
                presenter.verifyReffer(refId, uid, phone_num, name_);
                submitBtn.setClickable(false);
            } else {
                showToast("Please fill the form");
            }
        });
        loginBtn.setOnClickListener(v -> NavigateToActivity(AuthActivity.class, true, null));
    }

    private void InitializeView() {
        name = findViewById(R.id.name);
        refferedId = findViewById(R.id.reffered_id);
        submitBtn = findViewById(R.id.submitBtn);
        loginBtn = findViewById(R.id.loginBtn);
    }


    @Override
    public void InvalidError() {
        showToast("Invalid reffer");
        refferedId.getEditText().setText("");
        submitBtn.setClickable(true);
    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}