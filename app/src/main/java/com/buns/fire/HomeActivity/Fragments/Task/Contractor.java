package com.buns.fire.HomeActivity.Fragments.Task;

import androidx.annotation.NonNull;

import com.buns.fire.HomeActivity.Fragments.Task.Adapter.TasksRVAdapter;
import com.buns.fire.Models.Task;

import java.util.List;

public interface Contractor {
    interface View {
        void ShowLoading();

        void HideLoading();

        void setCompletedTasks(List<Task> completedTasks);
    }

    interface Presenter {
        List<Task> getTasks();

        void getCompletedTasks();

        void uploadTask(@NonNull Task task, @NonNull TasksRVAdapter.TaskUploadStatus listener);
    }
}
