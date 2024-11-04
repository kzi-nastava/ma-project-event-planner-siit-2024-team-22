package com.example.siit_2024_team_22_ma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterScreen extends AppCompatActivity {

    private EditText etEmailRegister;
    private EditText etPasswordRegister;
    private EditText etUsernameRegister;
    private EditText etPhoneNumberRegister;
    private Button btnConfirmRegister;
    private Button btnBackRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);

        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etUsernameRegister = findViewById(R.id.etUsernameRegister);
        etPhoneNumberRegister = findViewById(R.id.etPhoneNumberRegister);
        btnConfirmRegister = findViewById(R.id.btnConfirmRegister);
        btnBackRegister = findViewById(R.id.btnBackRegister);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnConfirmRegister.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, HomeScreen.class);
            startActivity(intent);
        });

        btnBackRegister.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });
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