package com.buns.fire.Utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.buns.fire.HomeActivity.Fragments.Task.Adapter.TasksRVAdapter;
import com.buns.fire.Models.CompletedTask;
import com.buns.fire.Models.Task;
import com.buns.fire.Models.TasksManager;
import com.buns.fire.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class SingletonUser {
    private static final String TAG = "SingletonUser";
    private User user;
    private static SingletonUser instance;
    private DocumentReference userRef;
    private List<Task> tasks;
    private TasksManager tManager;
    private OnSuccessListener<Void> listener;

    public User getUser() {
        return user;
    }

    public static SingletonUser getInstance() {
        if (instance == null)
            instance = new SingletonUser();
        return instance;
    }

    public static SingletonUser getInstance(@NonNull OnSuccessListener<Void> listener) {
        if (instance == null)
            instance = new SingletonUser();
        instance.listener = listener;
        return instance;
    }

    private SingletonUser() {
        listener = null;
        tManager = TasksManager.getInstance(null);
        tasks = new ArrayList<>();
        userRef = null;
        setCurrentUser();
    }


    private void setCurrentUser() {
        Constants
                .getUser()
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null) {
                        if (value.getDocuments().size() > 0) {
                            userRef = value.getDocuments().get(0).getReference();
                            user = value.getDocuments().get(0).toObject(User.class);
                            Constants.setUser(user);
                        }
                    }
                });
    }


    public void addToComTasks(@NonNull Task task, TasksRVAdapter.TaskUploadStatus listener) {
        CompletedTask ct = new CompletedTask();
        ct.setId(task.getId());
        ct.setTime(Constants.getCurrentDate());

        userRef.collection(Constants.tasks)
                .document(task.getId())
                .set(ct)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "addToComTasks: Successfull");
                    removeTask(task.getId());
                    IncrementBalance(task.getCost());
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "addToComTasks: Failed");
                    listener.onTaskUploadFailed(task);
                });

    }

    public void removeTask(@NonNull String id) {
        for (Task t : tasks) {
            if (TextUtils.equals(t.getId(), id)) {
                tasks.remove(t);
                return;
            }

        }
    }

    private void IncrementBalance(final int amount) {
        if (userRef != null) {
            userRef.update(Constants.balance, FieldValue.increment(amount));
        }
    }

    public List<Task> getTasksList() {
        return tManager.getIncompleteTasks();
    }

    public List<Task> getCompletedTaskList() {
        return tManager.getCompletedTasksList();
    }
}
