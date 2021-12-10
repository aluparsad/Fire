package com.buns.fire.HomeActivity.Fragments.VIP;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.Models.Pack;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VipFragment extends Fragment implements Contractor.View, AdapterVIP.OnClickListener {
    private Contractor.Presenter presenter;
    private LottieAnimationView loading;
    private RecyclerView rvPlans;

    public VipFragment() {
        presenter = new Presenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeView(view);
        presenter.getPlans();
    }


    private void InitializeView(View view) {
        Picasso.get().load(Constants.vipAdUrl).into((ImageView)view.findViewById(R.id.adVIPIV));
        loading = view.findViewById(R.id.loading);
        rvPlans = view.findViewById(R.id.rv_vip);
    }


    @Override
    public void setPlans(@NonNull List<Pack> plans) {
        showLoading();
        AdapterVIP adapter = new AdapterVIP(plans, getResources().getStringArray(R.array.card_colors),  this);
        rvPlans.setAdapter(adapter);
        rvPlans.setLayoutManager(new LinearLayoutManager(getContext()));
        hideLoading();
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }

    @Override
    public void successfull() {
        showToast("Successfull");
        hideLoading();
    }
    @Override
    public void error(@NonNull String msg) {
        showToast(msg);
        hideLoading();
    }

    @Override
    public void onClick(Pack pack) {
        showLoading();
        if(Constants.getIsLoggedIn())
            presenter.buySubscription(pack);
        else {
            hideLoading();
            showToast("Register/Login");
        }
    }

    private void showToast(@NonNull String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

