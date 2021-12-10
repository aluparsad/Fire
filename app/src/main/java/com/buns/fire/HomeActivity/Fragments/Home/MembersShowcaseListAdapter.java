package com.buns.fire.HomeActivity.Fragments.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buns.fire.Models.Membership;
import com.buns.fire.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MembersShowcaseListAdapter extends RecyclerView.Adapter<MembersShowcaseListAdapter.ViewHolder> {
    private List<Membership> data;

    public MembersShowcaseListAdapter() {
        data = new ArrayList<>(getMembersContent());
    }

    private void clear() {
        int range = data.size();
        data.clear();
        notifyItemRangeRemoved(0, range - 1);
    }

    public void setMembersContent() {
        clear();
        data.addAll(getMembersContent());
        notifyItemRangeInserted(0, data.size() - 1);
    }

    public void setMerchantsContent() {
        clear();
        data.addAll(getMerchantsContent());
        notifyItemRangeInserted(0, data.size() - 1);
    }


    private Collection<Membership> getMembersContent() {
        List<Membership> temp = new ArrayList<>();
        for (int i = 0; i <= 18; i++) {
            int num1 = randomNum();
            Membership m = new Membership("Congratulations ****" + (num1 <= 999 ? "0" + num1 : "" + num1), "Completed 20 tasks today");
            temp.add(m);
        }
        return temp;
    }

    private Collection<Membership> getMerchantsContent() {
        List<Membership> temp = new ArrayList<>();
        for (int i = 0; i <= 18; i++) {
            int num1 = randomNum(), num2 = randomNum();
            Membership m = new Membership("****" + (num1 <= 999 ? "0" + num1 : "" + num1),
                    "released " + (num2 <= 999 ? "0" + num2 : num2) + " tasks today");
            temp.add(m);
        }
        return temp;
    }

    private int randomNum() {
        return Math.abs((int) (Math.random() * 9999) - (int) (Math.random() * 1000));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null)
            holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Membership s) {
            ((TextView) itemView.findViewById(R.id.title_ms)).setText(s.getTitle());
            ((TextView) itemView.findViewById(R.id.content_ms)).setText(s.getContent());
//            ((ImageView) itemView.findViewById(R.id.ml_dp));
        }
    }
}
