package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class SettingsActivity extends AppCompatActivity {
    private TextView settingsTitleText;
    private CardView profileCard, notificationsCard, privacyCard, aboutCard;
    private Switch notificationSwitch, locationSwitch, emergencySwitch;
    private Button backButton, homeButton, logoutButton, saveButton;

    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        setupUI();
        setupClickListeners();
    }

    private void initializeViews() {
        settingsTitleText = findViewById(R.id.settings_title_text);
        profileCard = findViewById(R.id.profile_card);
        notificationsCard = findViewById(R.id.notifications_card);
        privacyCard = findViewById(R.id.privacy_card);
        aboutCard = findViewById(R.id.about_card);
        notificationSwitch = findViewById(R.id.notification_switch);
        locationSwitch = findViewById(R.id.location_switch);
        emergencySwitch = findViewById(R.id.emergency_switch);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        logoutButton = findViewById(R.id.logout_button);
        saveButton = findViewById(R.id.save_button);
    }

    private void setupUI() {
        settingsTitleText.setText("Settings");

        // Set default switch states
        notificationSwitch.setChecked(true);
        locationSwitch.setChecked(true);
        emergencySwitch.setChecked(true);
    }

    private void setupClickListeners() {
        profileCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        aboutCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });

        saveButton.setOnClickListener(v -> {
            // Save settings
            Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> {
            sessionManager.clearSession();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        backButton.setOnClickListener(v -> onBackPressed());

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}