package com.buns.fire.HomeActivity.Fragments.VIP;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buns.fire.Models.Pack;
import com.buns.fire.Models.Subscription;
import com.buns.fire.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterVIP extends RecyclerView.Adapter<AdapterVIP.ViewHolder> {

    private final OnClickListener listener;
    private List<Pack> data;
    private String[] colors;

    public AdapterVIP(@NonNull List<Pack> data, @NonNull String[] colorsArray, OnClickListener listener) {
        this.colors = colorsArray;
        this.data = new ArrayList<>();
        this.data.addAll(data);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vip_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pack pack = data.get(position);
        holder.setCardColor(position);
        holder.setData(pack);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price, numTasks;
        private Button joinBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeView(itemView);
        }

        private void InitializeView(View view) {
            title = view.findViewById(R.id.planTitle);
            price = view.findViewById(R.id.plan_price_tv);
            numTasks = view.findViewById(R.id.tasksValueTv);
            joinBtn = view.findViewById(R.id.joinNowBtn);
        }

        private void setData(@NonNull Pack pack) {
            title.setText(pack.getTitle().toString());
            price.setText(String.valueOf(pack.getPrice()));
            numTasks.setText(String.valueOf(pack.getTasks()));
            if (pack.getTitle().equals(Subscription.VISITOR)) {
                joinBtn.setVisibility(View.INVISIBLE);
                joinBtn.setClickable(false);
            } else {
                joinBtn.setOnClickListener(v -> listener.onClick(pack));
            }
        }

        private void setCardColor(final int pos) {
            ((CardView) itemView.findViewById(R.id.card_vip)).setCardBackgroundColor(Color.parseColor(colors[pos]));
        }
    }

    public interface OnClickListener {
        void onClick(Pack pack);
    }

}
