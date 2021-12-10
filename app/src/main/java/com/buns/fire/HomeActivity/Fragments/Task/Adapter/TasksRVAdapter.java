package com.buns.fire.HomeActivity.Fragments.Task.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buns.fire.Models.Task;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;
import com.buns.fire.Utils.SubscriptionCost;

import java.util.ArrayList;
import java.util.List;

public class TasksRVAdapter extends RecyclerView.Adapter<TasksRVAdapter.ViewHolder> {
    private List<Task> data;
    private UploadTask listener;

    public TasksRVAdapter(@NonNull List<Task> tasksList, @NonNull Task.TaskType type, @NonNull UploadTask listener) {
        data = new ArrayList<>();
        this.listener = listener;
        for (Task t : tasksList) {
            if (t.getType().equals(type))
                data.add(t);
        }
    }


    public void RemoveDataItem(@NonNull Task task) {
        final int index = data.indexOf(task);
        data.remove(index);
        notifyItemRemoved(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task t = data.get(position);
        t.setCost(SubscriptionCost.getInstance().getTaskCost());
        holder.setTaskView(t);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements TasksRVAdapter.TaskUploadStatus {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void setTaskView(Task task) {

            //Set Task Title
            ((TextView) itemView.findViewById(R.id.taskTitle))
                    .setText(task.getType().toString());

            //Set Task Logo
            setTasktyp(task.getType());

            //Set Task Plan Type
            ((TextView) itemView.findViewById(R.id.taskSubplan))
                    .setText(Constants.getCurrentUser().getPlan().toString());

            //Set Task Cost
            ((TextView) itemView.findViewById(R.id.taskCost)).setText(String.valueOf(task.getCost()));

            //Set Task Url
            ((TextView) itemView.findViewById(R.id.taskUrl)).setText(task.getLink());

            //Set Task Description
            ((TextView) itemView.findViewById(R.id.task_description)).setText(task.getDescription());

            //set Url Click Listener
            itemView.setOnClickListener(v -> listener.openLink(task.getLink()));

            //Set Task Upload Button
            itemView.findViewById(R.id.taskUploadBtn)
                    .setOnClickListener(v -> {
                        RemoveDataItem(task);
                        listener.uploaded(task, this);

                    });
        }

        private void setTasktyp(Task.TaskType type) {
            @DrawableRes int logoId;
            if (type.equals(Task.TaskType.INSTAGRAM))
                logoId = R.mipmap.instagram_background;
            else if (type.equals(Task.TaskType.FACEBOOK))
                logoId = R.mipmap.facebook_background;
            else if (type.equals(Task.TaskType.LINE))
                logoId = R.mipmap.line_background;
            else
                logoId = R.mipmap.youtube_background;
            ((ImageView) itemView.findViewById(R.id.taskLogo)).setImageResource(logoId);
        }

        @Override
        public void onTaskUploadFailed(@NonNull Task task) {
            data.add(0, task);
            notifyItemInserted(0);
        }
    }

    public interface UploadTask {
        void uploaded(@NonNull Task task, @NonNull TaskUploadStatus listener);

        void openLink(@NonNull String url);
    }

    public interface TaskUploadStatus {
        void onTaskUploadFailed(@NonNull Task task);
    }

}

