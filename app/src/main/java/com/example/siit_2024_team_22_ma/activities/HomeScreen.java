package com.example.siit_2024_team_22_ma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.adapters.EventListAdapter;
import com.example.siit_2024_team_22_ma.models.Event;
import com.example.siit_2024_team_22_ma.adapters.ServiceListAdapter;
import com.example.siit_2024_team_22_ma.models.Service;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private ListView listView;
    private EventListAdapter eventListAdapter;
    private ServiceListAdapter serviceListAdapter;
    private ArrayList<Event> events;
    private ArrayList<Service> services;
    private boolean isShowingEvents = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        listView = findViewById(R.id.event_list);

        events = new ArrayList<>();
        events.add(new Event(R.drawable.event_image2, "Concert at the Square", "Open-air concert featuring popular bands.", false));
        events.add(new Event(R.drawable.event_image1, "Art Exhibition", "Exhibition of modern artists in the city center.", true));

        services = new ArrayList<>();
        services.add(new Service("Web Development", "Professional web development services", "Tech Co.", "contact@techco.com", R.drawable.service_image1));
        services.add(new Service("Graphic Design", "Creative graphic design services", "Design Studio", "info@designstudio.com", R.drawable.service_image2));

        eventListAdapter = new EventListAdapter(this, events);
        serviceListAdapter = new ServiceListAdapter(this, services);

        listView.setAdapter(eventListAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (isShowingEvents) {
                Event selectedEvent = events.get(position);
                if (selectedEvent != null) {
                    Intent intent = new Intent(HomeScreen.this, EventDetailActivity.class);
                    intent.putExtra("event", selectedEvent);
                    startActivity(intent);
                }
            } else {
                Service selectedService = services.get(position);
                if (selectedService != null) {
                    Intent intent = new Intent(HomeScreen.this, ServiceDetailActivity.class);
                    intent.putExtra("service", selectedService);
                    startActivity(intent);
                }
            }
        });

        Button toggleViewButton = findViewById(R.id.toggle_view_button);
        toggleViewButton.setOnClickListener(v -> {
            if (isShowingEvents) {
                showServices();
            } else {
                showEvents();
            }
            isShowingEvents = !isShowingEvents;
        });

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {

        });

        Button filterButton = findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> {

        });
    }

    private void showEvents() {
        listView.setAdapter(eventListAdapter);
    }

    private void showServices() {
        listView.setAdapter(serviceListAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}