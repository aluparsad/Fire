package com.buns.fire.SplashScreen;

import android.os.Bundle;

import com.buns.fire.BaseActivity.BaseActivity;
import com.buns.fire.HomeActivity.Activity.HomeActivity;
import com.buns.fire.Models.TasksManager;
import com.buns.fire.R;
import com.google.android.gms.tasks.OnSuccessListener;

public class SplashActivity extends BaseActivity implements Contractor.View, OnSuccessListener<Void> {

    private Contractor.Presenter presenter;
    private TasksManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        InitialiseConstants();
        tm = TasksManager.getInstance(null);
    }

    private void InitialiseConstants() {
        presenter = new Presenter(this);
        presenter.Initialize();
    }

    @Override
    public void navigate() {
        tm.Init();
    }

    @Override
    public void onSuccess(Void aVoid) {
        tm.setListener(null);
        NavigateToActivity(HomeActivity.class, true, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tm.setListener(this);
    }
}