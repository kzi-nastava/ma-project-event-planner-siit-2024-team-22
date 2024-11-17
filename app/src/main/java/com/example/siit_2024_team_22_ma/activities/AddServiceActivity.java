package com.example.siit_2024_team_22_ma.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.siit_2024_team_22_ma.R;

public class AddServiceActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;

    private EditText editTextTitle, editTextDescription, editTextCompany, editTextContact;
    private Button btnUploadImage, buttonSubmit;
    private ImageView ivImagePreview;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextContact = findViewById(R.id.editTextContact);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        ivImagePreview = findViewById(R.id.iv_image_preview);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set listeners
        btnUploadImage.setOnClickListener(v -> openImagePicker());
        buttonSubmit.setOnClickListener(v -> submitForm());
    }

    // Opens image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // Handle selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            selectedImageUri = data.getData();
            ivImagePreview.setImageURI(selectedImageUri);
        }
    }

    // Handle form submission
    private void submitForm() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String company = editTextCompany.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        // Validate input
        if (title.isEmpty() || description.isEmpty() || company.isEmpty() || contact.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would create a new Service object or process the data further as needed
        Toast.makeText(this, "Service submitted successfully!", Toast.LENGTH_SHORT).show();

        // Clear form (optional)
        clearForm();
    }

    // Clears form fields
    private void clearForm() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        editTextCompany.setText("");
        editTextContact.setText("");
        ivImagePreview.setImageURI(null);
        selectedImageUri = null;
    }
}
