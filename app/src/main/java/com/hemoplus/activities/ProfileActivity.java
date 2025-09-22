
package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hemoplus.R;
import com.hemoplus.database.DatabaseHelper;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    private TextView emailText, bloodTypeText, userTypeText, joinDateText;
    private EditText fullNameEdit, phoneEdit;
    private Button updateButton, backButton, homeButton;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        populateUserData();
        setupClickListeners();
    }

    private void initializeViews() {
        emailText = findViewById(R.id.email_text);
        bloodTypeText = findViewById(R.id.blood_type_text);
        userTypeText = findViewById(R.id.user_type_text);
        joinDateText = findViewById(R.id.join_date_text);
        fullNameEdit = findViewById(R.id.full_name_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        updateButton = findViewById(R.id.update_button);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
    }

    private void populateUserData() {
        emailText.setText(currentUser.getEmail());
        bloodTypeText.setText(currentUser.getBloodType());
        userTypeText.setText(currentUser.getUserType().toUpperCase());
        fullNameEdit.setText(currentUser.getFullName());
        phoneEdit.setText(currentUser.getPhone());
        joinDateText.setText("Member since 2024");
    }

    private void setupClickListeners() {
        updateButton.setOnClickListener(v -> updateProfile());

        backButton.setOnClickListener(v -> onBackPressed());

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void updateProfile() {
        String fullName = fullNameEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();

        if (fullName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setFullName(fullName);
        currentUser.setPhone(phone);

        boolean success = dbHelper.updateUser(currentUser);
        if (success) {
            sessionManager.saveUserSession(currentUser);
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}