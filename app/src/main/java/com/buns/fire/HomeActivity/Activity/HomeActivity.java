package com.buns.fire.HomeActivity.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.buns.fire.Authentication.AuthActivity;
import com.buns.fire.BaseActivity.BaseActivity;
import com.buns.fire.HomeActivity.Fragments.Home.HomeFragment;
import com.buns.fire.HomeActivity.Fragments.OpenTab;
import com.buns.fire.HomeActivity.Fragments.Profile.ProfileFragment;
import com.buns.fire.HomeActivity.Fragments.Task.TaskFragment;
import com.buns.fire.HomeActivity.Fragments.VIP.VipFragment;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;

public class HomeActivity extends BaseActivity implements OpenTab, HomeContractor.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private HomeContractor.Presenter presenter;

    private void InitializeView() {
        presenter = new Presenter(this);
        fragContainerId = R.id.frameLayout;
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        NavigateToFragment(new HomeFragment(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeView();
    }

    @Override
    public void openTaskTab(@NonNull String task) {
        ShowLoading();
        if (!Constants.getIsLoggedIn())
            NavigateToActivity(AuthActivity.class, true, null);
        else {
            bottomNavigationView.setSelectedItemId(R.id.task);
            NavigateToFragment(new TaskFragment(task));
        }
        HideLoading();
    }

    @Override
    public void switchFrag(@NonNull Fragment fragment) {
        @IdRes int tabId;
        if (fragment instanceof VipFragment)
            tabId = R.id.vip;
        else if (fragment instanceof ProfileFragment)
            tabId = R.id.my;
        else if (fragment instanceof TaskFragment)
            tabId = R.id.task;
        else
            tabId = R.id.home;
        bottomNavigationView.setSelectedItemId(tabId);
        NavigateToFragment(fragment);
    }

    @Override
    public void ShowLoading() {

    }

    @Override
    public void HideLoading() {

    }

    @Override
    public void isEligible(@NonNull Boolean status) {
        if (status) {
            NavigateToFragment(new TaskFragment("youtube"));
            HideLoading();
        } else {
            Toast.makeText(this, "Upgrade Plan", Toast.LENGTH_SHORT).show();
            switchFrag(new HomeFragment(this));
            HideLoading();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task:
                ShowLoading();
                if (Constants.getIsLoggedIn()) {
                    try {
                        presenter.getIsEligible();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    NavigateToActivity(AuthActivity.class, true, null);
                }
                break;

            case R.id.vip:
                NavigateToFragment(new VipFragment());
                break;

            case R.id.my:
                if (!Constants.getIsLoggedIn())
                    NavigateToActivity(AuthActivity.class, true, null);
                else
                    NavigateToFragment(new ProfileFragment());
                break;

            default:
                NavigateToFragment(new HomeFragment(this));
                return true;
        }
        return true;
    }
}