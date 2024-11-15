package com.example.siit_2024_team_22_ma.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.models.Event;

public class EventDetailActivity extends AppCompatActivity {

    private TextView eventTitle, eventDescription, eventOrganization;
    private ImageView eventImage;
    private Button backButton, acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventTitle = findViewById(R.id.event_title);
        eventDescription = findViewById(R.id.event_description);
        eventOrganization = findViewById(R.id.event_organization);
        eventImage = findViewById(R.id.event_image);
        backButton = findViewById(R.id.back_button);
        acceptButton = findViewById(R.id.accept_button);

        Event event = (Event) getIntent().getSerializableExtra("event");
        int eventImageRes = getIntent().getIntExtra("event_image", -1);

        if (event != null) {
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());
            eventOrganization.setText("Organization: " + event.getTitle());

            if (eventImageRes != -1) {
                eventImage.setImageResource(eventImageRes);
            }

            if (event.isPrivate()) {
                acceptButton.setVisibility(View.GONE);
            } else {
                acceptButton.setVisibility(View.VISIBLE);
            }
        }

        backButton.setOnClickListener(v -> finish());
        acceptButton.setOnClickListener(v -> {});
    }
}