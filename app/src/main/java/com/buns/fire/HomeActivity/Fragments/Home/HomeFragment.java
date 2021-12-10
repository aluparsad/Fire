package com.buns.fire.HomeActivity.Fragments.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buns.fire.HomeActivity.Fragments.OpenTab;
import com.buns.fire.HomeActivity.Fragments.VIP.VipFragment;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;

public class HomeFragment extends HomeParent implements Contractor.View {
    private OpenTab taskOpener;
    private Contractor.Presenter presenter;

    public HomeFragment(OpenTab opener) {
        this.presenter = new PresenterHome(this);
        taskOpener = opener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(@NonNull View view) {
        announcement.setText(R.string.broadcast);
        view.findViewById(R.id.helpBtn).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.helpSupportUrl));
            startActivity(browserIntent);
        });
    }

    @Override
    protected void basicHallBtns(@NonNull Button vipBtn, @NonNull Button vidTutorial) {
        super.basicHallBtns(vipBtn, vidTutorial);

        vipBtn.setOnClickListener(v -> taskOpener.switchFrag(new VipFragment()));
        vidTutorial.setOnClickListener(v -> Toast.makeText(getContext(), "Pending..", Toast.LENGTH_SHORT).show());
        inviteBtn.setOnClickListener(v-> presenter.copyRefferCode(getContext()));
    }


    @Override
    protected void facebookBtn() {
        super.facebookBtn();
        taskOpener.openTaskTab("facebook");
    }

    @Override
    protected void instagramBtn() {
        super.instagramBtn();
        taskOpener.openTaskTab("instagram");
    }

    @Override
    protected void youtubeBtn() {
        super.youtubeBtn();
        taskOpener.openTaskTab("youtube");
    }

    @Override
    protected void lineBtn() {
        super.lineBtn();
        taskOpener.openTaskTab("line");
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

