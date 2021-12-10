package com.buns.fire.HomeActivity.Fragments.Task;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.buns.fire.HomeActivity.Fragments.Task.Adapter.TasksRVAdapter;
import com.buns.fire.Models.Task;
import com.buns.fire.Utils.SingletonUser;

import java.util.List;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public List<Task> getTasks() {
        return SingletonUser.getInstance().getTasksList();
    }

    @Override
    public void getCompletedTasks() {
        mView.ShowLoading();
        List<Task> t = SingletonUser.getInstance().getCompletedTaskList();
        mView.setCompletedTasks(t);
        mView.HideLoading();
    }

    @Override
    public void uploadTask(@NonNull Task task, @NonNull TasksRVAdapter.TaskUploadStatus listener) {
        mView.ShowLoading();
        SingletonUser su = SingletonUser.getInstance();
        boolean isAdded = false;

        for (Task ct : su.getCompletedTaskList()) {
            if (TextUtils.equals(ct.getId(), task.getId())) {
                isAdded = true;
            }
        }

        if (!isAdded)
            su.addToComTasks(task, listener);
        else
            su.removeTask(task.getId());
        mView.HideLoading();
    }

}
