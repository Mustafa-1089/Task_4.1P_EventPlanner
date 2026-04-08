package com.example.task_41p_eventplanner.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task_41p_eventplanner.R;
import com.example.task_41p_eventplanner.data.Event;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    public interface OnEventClickListener { void onClick(Event event); }

    private final OnEventClickListener onEdit;
    private final OnEventClickListener onDelete;

    public EventAdapter(OnEventClickListener onEdit, OnEventClickListener onDelete) {
        super(DIFF_CALLBACK);
        this.onEdit   = onEdit;
        this.onDelete = onDelete;
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override public boolean areItemsTheSame(@NonNull Event a, @NonNull Event b) {
                    return a.id == b.id;
                }
                @Override public boolean areContentsTheSame(@NonNull Event a, @NonNull Event b) {
                    return a.title.equals(b.title) && a.dateTime.equals(b.dateTime);
                }
            };

    @NonNull @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new EventViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = getItem(position);
        holder.tvTitle.setText(event.title);
        holder.tvCategory.setText("📂 " + event.category);
        holder.tvLocation.setText("📍 " + event.location);
        holder.tvDateTime.setText("🕐 " + event.dateTime);
        holder.btnEdit.setOnClickListener(v -> onEdit.onClick(event));
        holder.btnDelete.setOnClickListener(v -> onDelete.onClick(event));
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvLocation, tvDateTime;
        Button btnEdit, btnDelete;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle    = itemView.findViewById(R.id.tvCardTitle);
            tvCategory = itemView.findViewById(R.id.tvCardCategory);
            tvLocation = itemView.findViewById(R.id.tvCardLocation);
            tvDateTime = itemView.findViewById(R.id.tvCardDateTime);
            btnEdit    = itemView.findViewById(R.id.btnEdit);
            btnDelete  = itemView.findViewById(R.id.btnDelete);
        }
    }
}