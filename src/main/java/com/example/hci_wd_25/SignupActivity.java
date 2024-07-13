package com.example.hci_wd_25;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hci_wd_25.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupWindowInsets();
        initializeDatabaseHelper();
        setupClickListeners();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeDatabaseHelper() {
        databaseHelper = new DatabaseHelper(this);
    }

    private void setupClickListeners() {
        binding.signupButton.setOnClickListener(view -> attemptSignup());

        binding.loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    private void attemptSignup() {
        String email = binding.signupEmail.getText().toString().trim();
        String password = binding.signupPassword.getText().toString().trim();
        String confirmPassword = binding.signupConfirm.getText().toString().trim();

        if (validateInputs(email, password, confirmPassword)) {
            if (!databaseHelper.checkEmail(email)) {
                boolean insert = databaseHelper.insertData(email, password);
                if (insert) {
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "User Already Exists, Please Login", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        if (email.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!databaseHelper.isValidEmail(email)) {
            Toast.makeText(SignupActivity.this, "Invalid Email Format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Password is Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!databaseHelper.isValidPassword(password)) {
            Toast.makeText(SignupActivity.this, "Password Must Be At Least 8 Characters Long and Include Uppercase, Lowercase, Digit, and Special Character", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignupActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
