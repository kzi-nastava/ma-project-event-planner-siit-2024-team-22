package com.example.siit_2024_team_22_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.models.Event;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {

    public EventListAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.event_card, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_card, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.event_image);
        TextView titleView = convertView.findViewById(R.id.event_title);
        TextView descriptionView = convertView.findViewById(R.id.event_description);
        TextView privacyView = convertView.findViewById(R.id.event_privacy);

        if (event != null) {
            imageView.setImageResource(event.getImage());
            titleView.setText(event.getTitle());
            descriptionView.setText(event.getDescription());
            privacyView.setText(event.isPrivate() ? "Private" : "Public");
        }

        return convertView;
    }
}