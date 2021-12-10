package com.buns.fire.BaseActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface Navigator {
    void NavigateToActivity(@NonNull Class<?> cls, @NonNull Boolean doFinish, Bundle bundle);

    void NavigateToFragment(@NonNull Fragment fragment);
}
