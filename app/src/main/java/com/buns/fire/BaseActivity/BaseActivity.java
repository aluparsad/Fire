package com.buns.fire.BaseActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.buns.fire.Utils.Authentication;

public class BaseActivity extends AppCompatActivity implements Navigator {
    protected @IdRes
    int fragContainerId;

    public void NavigateToFragment(@NonNull Fragment fragment) {
//        TODO:Finish the current fragment to save memory consumption

        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragContainerId, fragment)
                .disallowAddToBackStack()
                .commit();
    }


    public void NavigateToActivity(@NonNull Class<?> cls, @NonNull Boolean doFinish, Bundle bundle) {
        Intent i = new Intent(this, cls);
        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
        if (doFinish) finish();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Authentication.setBaseActivity(this);
        getSupportActionBar().hide();                                                               //Hides Support Action Bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);                    //Activates Night Mode

    }
}
