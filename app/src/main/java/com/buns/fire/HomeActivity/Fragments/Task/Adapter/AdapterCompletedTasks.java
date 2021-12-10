package com.buns.fire.HomeActivity.Fragments.Task.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buns.fire.Models.Task;
import com.buns.fire.R;
import com.buns.fire.Utils.Constants;
import com.buns.fire.Utils.SubscriptionCost;

import java.util.ArrayList;
import java.util.List;

public class AdapterCompletedTasks extends RecyclerView.Adapter<AdapterCompletedTasks.ViewHolder> {

    private List<Task> tasksdata;
    private String[] colors;

    public AdapterCompletedTasks(@NonNull List<Task> tasksdata, @NonNull String[] colorsArray) {
        this.tasksdata = new ArrayList<>();
        this.tasksdata.addAll(tasksdata);
        this.colors = colorsArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task t = tasksdata.get(position);
        if (t != null) {
            final int index = position % 5;
            ((CardView) holder.itemView.findViewById(R.id.taskCard)).setCardBackgroundColor(Color.parseColor(colors[index]));
            t.setCost(SubscriptionCost.getInstance().getTaskCost());
            holder.setTaskView(t);
        }
    }

    @Override
    public int getItemCount() {
        return tasksdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

            itemView.findViewById(R.id.taskUploadBtn).setVisibility(View.GONE);

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
    }
}
