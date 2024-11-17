package com.example.siit_2024_team_22_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.models.Event;
import com.example.siit_2024_team_22_ma.models.Service;

import java.util.ArrayList;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    private Context context;
    private ArrayList<?> items;
    private OnItemClickListener listener;

    public CarouselAdapter(Context context, ArrayList<?> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_card, parent, false);
        view.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (items.get(position) instanceof Event) {
            Event event = (Event) items.get(position);
            holder.imageView.setImageResource(event.getImage());
            holder.titleView.setText(event.getTitle());
            holder.descriptionView.setText(event.getDescription());
        } else if (items.get(position) instanceof Service) {
            Service service = (Service) items.get(position);
            holder.imageView.setImageResource(service.getImageResource());
            holder.titleView.setText(service.getTitle());
            holder.descriptionView.setText(service.getDescription());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image);
            titleView = itemView.findViewById(R.id.carousel_title);
            descriptionView = itemView.findViewById(R.id.carousel_description);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}