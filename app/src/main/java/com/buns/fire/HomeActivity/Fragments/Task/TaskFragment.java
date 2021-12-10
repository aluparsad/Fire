package com.buns.fire.HomeActivity.Fragments.Task;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.fire.HomeActivity.Fragments.Task.Adapter.AdapterCompletedTasks;
import com.buns.fire.HomeActivity.Fragments.Task.Adapter.TasksRVAdapter;
import com.buns.fire.Models.Task;
import com.buns.fire.R;
import com.buns.fire.Utils.SingletonUser;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaskFragment extends Fragment implements Contractor.View, TasksRVAdapter.UploadTask {

    private static final int REQUEST_CODE = 101;
    private Contractor.Presenter presenter;
    private TabLayout tasksTabLayout;
    private RecyclerView tasksRv;
    private final String tabName;
    private TasksRVAdapter adapter;
    private LottieAnimationView loading;
    private TasksRVAdapter.TaskUploadStatus templistener;
    private Task tempTask;

    public TaskFragment(String tabName) {
        //Initialize Constants
        presenter = new Presenter(this);
        adapter = new TasksRVAdapter(new ArrayList<>(), Task.TaskType.YOUTUBE, this);
        this.tabName = tabName.toLowerCase();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitializeView(view);

        tasksTabLayout
                .addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        String text = tab.getText().toString();
                        setTab(text);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
    }

    private void InitializeView(View view) {
        loading = view.findViewById(R.id.loading);
        tasksTabLayout = view.findViewById(R.id.tasks_tablayout);
        tasksRv = view.findViewById(R.id.tasks_rv);

        tasksRv.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRv.setHasFixedSize(true);
        tasksRv.setAdapter(adapter);

        setTab(tabName);
    }

    private void setTab(@NonNull String tabName) {
        tabName = tabName.toLowerCase();
        if ("youtube".contentEquals(tabName)) {
            tasksTabLayout.selectTab(tasksTabLayout.getTabAt(0));
            setTaskAdapter(Task.TaskType.YOUTUBE);
        } else if ("instagram".contentEquals(tabName)) {
            tasksTabLayout.selectTab(tasksTabLayout.getTabAt(1));
            setTaskAdapter(Task.TaskType.INSTAGRAM);
        } else if ("facebook".contentEquals(tabName)) {
            tasksTabLayout.selectTab(tasksTabLayout.getTabAt(2));
            setTaskAdapter(Task.TaskType.FACEBOOK);
        } else if ("line".contentEquals(tabName)) {
            tasksTabLayout.selectTab(tasksTabLayout.getTabAt(3));
            setTaskAdapter(Task.TaskType.LINE);
        } else {
            ShowLoading();
            tasksTabLayout.selectTab(tasksTabLayout.getTabAt(4));
            presenter.getCompletedTasks();
        }
    }


    private void setTaskAdapter(@NonNull Task.TaskType taskType) {
        ShowLoading();
        tasksRv.setAdapter(new TasksRVAdapter(presenter.getTasks(), taskType, this));
        HideLoading();
    }


    @Override
    public void setCompletedTasks(List<Task> completedTasks) {
        tasksRv.setAdapter(new AdapterCompletedTasks(completedTasks, getResources().getStringArray(R.array.card_colors)));
    }

    @Override
    public void uploaded(@NonNull Task task, @NonNull TasksRVAdapter.TaskUploadStatus listener) {
        if (SingletonUser.getInstance().getCompletedTaskList().size() <= 20) {
            ShowLoading();
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, REQUEST_CODE);
            tempTask = task;
            templistener = listener;
        } else {
            showToast("Limit reached.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                if (templistener != null && tempTask != null) {
                    new Handler(Looper.getMainLooper())
                            .postDelayed(() -> presenter.uploadTask(tempTask, templistener), 2000);      //2sec delay contineu
                    showToast("Task Completed");
                } else {
                    showToast("Something went wrong");
                }
            } else {
                showToast("Select ScreenShot");
            }
        }
        HideLoading();
    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openLink(@NonNull String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void ShowLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }

    @Override
    public void HideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }
}