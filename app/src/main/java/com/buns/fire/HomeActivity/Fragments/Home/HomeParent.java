package com.buns.fire.HomeActivity.Fragments.Home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class HomeParent extends Fragment {
    protected View basicHall, taskHall, businessHall;
    protected TextView announcement;
    protected ImageButton inviteBtn;
    protected LottieAnimationView loading;

    private void InitializeView(View view) {
        inviteBtn = view.findViewById(R.id.inviteBtn);
        announcement = view.findViewById(R.id.announcementTv);
        loading = view.findViewById(R.id.loading);
        setHalls(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeView(view);
        setDataView(view);
    }

    private void setDataView(@NonNull View view) {
        setAds(view);
        MembersShowcaseListAdapter adapter = new MembersShowcaseListAdapter();
        initRv(view, adapter);

        view.findViewById(R.id.membershiplist_btn).setOnClickListener(v -> adapter.setMembersContent());
        view.findViewById(R.id.merchantlist_btn).setOnClickListener(v -> adapter.setMerchantsContent());
    }

    private void initRv(@NonNull View view, @NonNull MembersShowcaseListAdapter adapter) {
        RecyclerView rv = view.findViewById(R.id.rv_empl_data);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstItemVisible = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (firstItemVisible != 0 && firstItemVisible % 18 == 0) {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }
            }
        });
        autoScroll(rv);
    }

    private void autoScroll(@NonNull RecyclerView recyclerView) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                recyclerView.scrollToPosition(++index);
                if (index == 18)
                    index = 0;
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void setAds(@NonNull View view) {
        ImageView iv = view.findViewById(R.id.adIV);
        Picasso.get().load(Constants.adUrl).into(iv);
    }

    private void setHalls(View v) {
        basicHall = v;
        taskHall = v.findViewById(R.id.taskHall);
        businessHall = v.findViewById(R.id.businessHall);

        setBasicHall();
        setTaskHall();
        setBusinessHall();
    }

    private void setBusinessHall() {
        TextView titleBH = businessHall.findViewById(R.id.title);
        titleBH.setText("Business hall");
        titleBH.setTextSize(14f);
        setSocialButtons(businessHall);
    }

    private void setTaskHall() {
        ((TextView) taskHall.findViewById(R.id.title)).setText("Task hall");
        setSocialButtons(taskHall);
    }

    private void setBasicHall() {
        final MaterialButton vipArea, videoTutorial;

        basicHall.findViewById(R.id.title).setVisibility(View.GONE);

        vipArea = basicHall.findViewById(R.id.vipareaBtn);
        videoTutorial = basicHall.findViewById(R.id.vid_tutoBnt);

        setBtnSize(12, 80, vipArea, videoTutorial);
        setBtn(vipArea, R.string.vipArea, R.mipmap.vip_foreground);
        setBtn(videoTutorial, R.string.videoTutorial, R.mipmap.play_foreground);

        basicHallBtns(vipArea, videoTutorial);
    }

    protected void basicHallBtns(@NonNull Button vipBtn, @NonNull Button vidTutorial) {
    }

    private void setSocialButtons(@NonNull View view) {
        MaterialButton youtube, instagram, facebook, line;

        youtube = view.findViewById(R.id.btn1);
        instagram = view.findViewById(R.id.btn2);
        facebook = view.findViewById(R.id.btn3);
        line = view.findViewById(R.id.btn4);

        setBtn(youtube, R.string.youtube, R.mipmap.youtube_background);
        setBtn(instagram, R.string.instagram, R.mipmap.instagram_background);
        setBtn(facebook, R.string.facebook, R.mipmap.facebook_background);
        setBtn(line, R.string.line, R.mipmap.line_background);

        youtube.setOnClickListener(v -> youtubeBtn());
        instagram.setOnClickListener(v -> instagramBtn());
        facebook.setOnClickListener(v -> facebookBtn());
        line.setOnClickListener(v -> lineBtn());
    }

    private void setBtnSize(@NonNull int fontSize, @NonNull int iconSize, MaterialButton... btns) {
        for (MaterialButton btn : btns) {
            btn.setTextSize(fontSize);
            btn.setIconSize(iconSize);
        }
    }

    private void setBtn(@NonNull MaterialButton btn, @StringRes int text, @DrawableRes int iconId) {
        btn.setText(text);
        btn.setIcon(AppCompatResources.getDrawable(getContext(), iconId));
    }

    protected void facebookBtn() {
    }

    protected void instagramBtn() {
    }

    protected void youtubeBtn() {
    }

    protected void lineBtn() {
    }

}
