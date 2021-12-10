package com.buns.fire.Models;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.buns.fire.Utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TasksManager {
    private static final String TAG = "TM";
    private List<Task> completed, allTasks;
    private OnSuccessListener<Void> listener;
    private Boolean tasks = false, userRef = false;
    private static TasksManager instance;

    public static TasksManager getInstance(OnSuccessListener<Void> listener) {
        if (instance == null)
            instance = new TasksManager(listener);
        return instance;
    }

    private TasksManager(OnSuccessListener<Void> listener) {
        this.listener = listener;
        completed = new ArrayList<>();
        allTasks = new ArrayList<>();
    }


    public void Init() {
        getCompletedTasks();
        getTasks();
    }

    private void getTasks() {
        if (Constants.getCurrentUser() != null) {
            Constants
                    .getTasksRef(Constants.getCurrentUser().getPlan(), Constants.getCurrentDate())
                    .limit(20)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            return;
                        }

                        if (value != null) {
                            if (value.getDocuments().size() > 0) {
                                allTasks.clear();
                                for (DocumentSnapshot ds : value.getDocuments()) {
                                    allTasks.add(ds.toObject(Task.class));
                                }
                            }
                        }
                        tasks = true;
                        Notify();
                    });
        } else {
            tasks = true;
            Notify();
        }
    }

    private void getCompletedTasks() {
        if (Constants.getCurrentUser() != null) {
            Constants
                    .getUser()
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.getDocuments().size() > 0) {
                            getCompletedTasks2(queryDocumentSnapshots.getDocuments().get(0).getReference().collection(Constants.tasks));
                        }
                    });
        } else {
            userRef = true;
            Notify();
        }
    }

    private void getCompletedTasks2(@NonNull CollectionReference cs) {
        cs
                .whereEqualTo(Constants.time, Constants.getCurrentDate())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null) {
                        if (value.getDocuments().size() > 0) {
                            completed.clear();
                            for (DocumentSnapshot ds : value.getDocuments()) {
                                addToCompleted(ds.toObject(CompletedTask.class));
                            }
                        }
                    }
                    userRef = true;
                    Notify();
                });

    }

    private void addToCompleted(@NonNull CompletedTask ct) {
        for (Task t : allTasks) {
            if (TextUtils.equals(ct.getId(), t.getId())) {
                completed.add(t);
            }
        }
        Log.d(TAG, "addToCompleted: size: " + completed.size());
    }

    private boolean check() {
        return userRef && tasks;
    }

    private void Notify() {
        if (check() && listener != null) {
            listener.onSuccess(null);
        }
    }


    public List<Task> getIncompleteTasks() {
        List<Task> temp = new ArrayList<>(allTasks);

        for (Task t : allTasks) {
            for (Task ct : completed) {
                if (TextUtils.equals(t.getId(), ct.getId())) {
                    temp.remove(t);
                }
            }
        }
        return temp;
    }


    public List<Task> getCompletedTasksList() {
        return completed;
    }

    public void setListener(OnSuccessListener<Void> listener) {
        this.listener = listener;
    }
}
