package com.buns.fire.Authentication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.buns.fire.Authentication.Registration.RegisterUserActivity;
import com.buns.fire.BaseActivity.BaseActivity;
import com.buns.fire.HomeActivity.Activity.HomeActivity;
import com.buns.fire.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


public class AuthActivity extends BaseActivity implements Contractor.View {

    private Contractor.Presenter presenter;

    private final String TAG = "authactivity";
    private String otp;

    private TextInputLayout numtil,otptil;
    private EditText number_field, otp_field;

    private CheckBox checkBox;
    private Button sendOtp, verifyBtn;

    private View numberView, otpView;

    private void InitializeView() {
        presenter = new Presenter(this);

        numtil = findViewById(R.id.et_number_field);
        otptil = findViewById(R.id.et_otp_field);

        number_field = numtil.getEditText();
        otp_field = otptil.getEditText();

        checkBox = findViewById(R.id.checkBox);
        sendOtp = findViewById(R.id.btn_sendOtp);
        verifyBtn = findViewById(R.id.btn_verify_otp);

        numberView = findViewById(R.id.phone_num_view);
        otpView = findViewById(R.id.otp_view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
        InitializeView();
        buttonClickListeners();

    }


    private void buttonClickListeners() {
        sendOtp.setOnClickListener(v -> {
            final String num = number_field.getText().toString().trim();

            if (!TextUtils.isEmpty(num) && num.length() == 10 && checkBox.isChecked()) {
                presenter.loginWithPhoneNumber(this, num);
                sendOtp.setClickable(false);
                sendOtp.setBackgroundColor(Color.LTGRAY);
            } else {
                try {
                    Exception e = new Exception(new Throwable("conditions not matched"));
                    onError(e);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        });

        verifyBtn.setOnClickListener(v -> {
            otp = otp_field.getText().toString().trim();
            if (!TextUtils.isEmpty(otp) && otp.length() == 6) {
                presenter.VerifyOTP(otp);
                verifyBtn.setClickable(false);
                verifyBtn.setBackgroundColor(Color.LTGRAY);
            }
        });
    }

    private void Login() {
        numberView.setVisibility(View.VISIBLE);
        otpView.setVisibility(View.GONE);
    }


    @Override
    public void onOtpSent() {
        numberView.setVisibility(View.GONE);
        otpView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "login Success", Toast.LENGTH_SHORT).show();
        NavigateToActivity(HomeActivity.class, true, null);
    }

    @Override
    public void onError(Exception e) {
        if (e.getMessage().equals("The sms code has expired. Please re-send the verification code to try again."))
            Toast.makeText(this, e.getMessage().substring(4, 26), Toast.LENGTH_SHORT).show();
        Login();
        Log.d(TAG, "onError: " + e.getMessage());
        sendOtp.setClickable(true);
    }

    @Override
    public void registeruser(FirebaseUser user) {
        Bundle bundle = new Bundle();
        bundle.putString("uid", user.getUid());
        bundle.putString("phone_num", user.getPhoneNumber());
        NavigateToActivity(RegisterUserActivity.class, true, bundle);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        NavigateToActivity(HomeActivity.class, true, null);
    }
}