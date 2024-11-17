package com.example.siit_2024_team_22_ma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.adapters.EventListAdapter;
import com.example.siit_2024_team_22_ma.adapters.ServiceListAdapter;
import com.example.siit_2024_team_22_ma.adapters.CarouselAdapter;
import com.example.siit_2024_team_22_ma.models.Event;
import com.example.siit_2024_team_22_ma.models.Service;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private ListView listView;
    private ViewPager2 viewPager2;
    private EventListAdapter eventListAdapter;
    private ServiceListAdapter serviceListAdapter;
    private ArrayList<Event> events;
    private ArrayList<Service> services;
    private ArrayList<?> carouselItems;
    private CarouselAdapter carouselAdapter;
    private boolean isShowingEvents = true;

    private boolean isDrawerOpen = false;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        drawerLayout = findViewById(R.id.drawer_layout);

        LinearLayout homeButton = findViewById(R.id.nav_home);
        homeButton.setOnClickListener(v -> {
            Toast.makeText(this, "Home placeholder", Toast.LENGTH_SHORT).show();
            closeDrawer();
        });

        LinearLayout settingsButton = findViewById(R.id.nav_settings);
        settingsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Settings placeholder", Toast.LENGTH_SHORT).show();
            closeDrawer();
        });

        LinearLayout addServiceButton = findViewById(R.id.nav_add_service);
        addServiceButton.setOnClickListener(v -> {
            Intent intent =  new Intent(HomeScreen.this, AddServiceActivity.class);
            startActivity(intent);
            closeDrawer();
        });


        LinearLayout logoutButton = findViewById(R.id.nav_logout);
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Logout placeholder", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
            startActivity(intent);
            closeDrawer();
            finish();
        });

        listView = findViewById(R.id.event_list);
        viewPager2 = findViewById(R.id.carousel_viewpager);

        events = new ArrayList<>();
        events.add(new Event(R.drawable.event_image2, "Concert at the Square", "Open-air concert featuring popular bands.", false));
        events.add(new Event(R.drawable.event_image1, "Art Exhibition", "Exhibition of modern artists in the city center.", true));
        events.add(new Event(R.drawable.event_image2, "Art Exhibition1", "Exhibition of modern artists in the city center.", true));
        events.add(new Event(R.drawable.event_image1, "Art Exhibition2", "Exhibition of modern artists in the city center.", true));
        events.add(new Event(R.drawable.event_image2, "Art Exhibition3", "Exhibition of modern artists in the city center.", true));


        services = new ArrayList<>();
        services.add(new Service("Web Development", "Professional web development services", "Tech Co.", "contact@techco.com", R.drawable.service_image1));
        services.add(new Service("Graphic Design", "Creative graphic design services", "Design Studio", "info@designstudio.com", R.drawable.service_image2));
        services.add(new Service("Graphic Design1", "Creative graphic design services", "Design Studio", "info@designstudio.com", R.drawable.service_image1));
        services.add(new Service("Graphic Design2", "Creative graphic design services", "Design Studio", "info@designstudio.com", R.drawable.service_image2));
        services.add(new Service("Graphic Design3", "Creative graphic design services", "Design Studio", "info@designstudio.com", R.drawable.service_image1));


        carouselAdapter = new CarouselAdapter(this, events, position -> {
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
        viewPager2.setAdapter(carouselAdapter);

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

    private void openDrawer() {
        drawerLayout.openDrawer(findViewById(R.id.custom_drawer));
        isDrawerOpen = true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(findViewById(R.id.custom_drawer));
        isDrawerOpen = false;
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void showEvents() {
        carouselAdapter = new CarouselAdapter(this, events, position -> {
            Event selectedEvent = events.get(position);
            if (selectedEvent != null) {
                Intent intent = new Intent(HomeScreen.this, EventDetailActivity.class);
                intent.putExtra("event", selectedEvent);
                startActivity(intent);
            }
        });
        viewPager2.setAdapter(carouselAdapter);
        listView.setAdapter(eventListAdapter);
    }

    private void showServices() {
        carouselAdapter = new CarouselAdapter(this, services, position -> {
            Service selectedService = services.get(position);
            if (selectedService != null) {
                Intent intent = new Intent(HomeScreen.this, ServiceDetailActivity.class);
                intent.putExtra("service", selectedService);
                startActivity(intent);
            }
        });
        viewPager2.setAdapter(carouselAdapter);
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