package com.example.smtuapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtuapp.R;
import com.example.smtuapp.models.DayItem;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<DayItem> days;
    private int selectedPosition = 0;
    private final OnDayClickListener listener;

    public interface OnDayClickListener {
        void onDayClick(int position);
    }

    public DayAdapter(List<DayItem> days, OnDayClickListener listener) {
        this.days = days;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DayItem item = days.get(position);
        holder.tvDayShort.setText(item.getShortName());
        holder.tvDate.setText(item.getDate());
        holder.itemView.setSelected(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int oldPos = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(oldPos);
            notifyItemChanged(position);
            listener.onDayClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayShort, tvDate;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayShort = itemView.findViewById(R.id.tvDayShort);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
