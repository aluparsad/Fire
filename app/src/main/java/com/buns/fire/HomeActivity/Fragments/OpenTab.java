package com.buns.fire.HomeActivity.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface OpenTab {
    void openTaskTab(@NonNull String task);
    void switchFrag(@NonNull Fragment fragment);
}
