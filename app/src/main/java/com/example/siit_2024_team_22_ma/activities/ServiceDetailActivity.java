package com.example.siit_2024_team_22_ma.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.models.Service;

public class ServiceDetailActivity extends AppCompatActivity {

    private ImageView serviceImage;
    private TextView serviceTitle, serviceDescription, serviceCompany, serviceContact;
    private Button backButton, acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        serviceImage = findViewById(R.id.service_image);
        serviceTitle = findViewById(R.id.service_title);
        serviceDescription = findViewById(R.id.service_description);
        serviceCompany = findViewById(R.id.service_company);
        serviceContact = findViewById(R.id.service_contact);
        backButton = findViewById(R.id.service_back_button);
        acceptButton = findViewById(R.id.service_accept_button);

        Service service = (Service) getIntent().getSerializableExtra("service");

        if (service != null) {
            serviceTitle.setText(service.getTitle());
            serviceDescription.setText(service.getDescription());
            serviceCompany.setText("Company: " + service.getCompany());
            serviceContact.setText("Contact Info: " + service.getContact());

            if (service.getImageResource() != 0) {
                serviceImage.setImageResource(service.getImageResource());
            }
        } else {
            // Если объект не передан, можно показать ошибку или вернуться на главный экран
            finish();  // Закрыть активность и вернуться на предыдущую
        }

        backButton.setOnClickListener(v -> finish());

        acceptButton.setOnClickListener(v -> {
            // Логика для кнопки "Accept"
        });
    }
}