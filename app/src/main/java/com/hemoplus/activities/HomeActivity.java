
package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeText, userStatsText;
    private CardView donorCard, recipientCard, historyCard, feedCard, settingsCard, aboutCard;

    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        welcomeText = findViewById(R.id.welcome_text);
        userStatsText = findViewById(R.id.user_stats_text);
        donorCard = findViewById(R.id.donor_card);
        recipientCard = findViewById(R.id.recipient_card);
        historyCard = findViewById(R.id.history_card);
        feedCard = findViewById(R.id.feed_card);
        settingsCard = findViewById(R.id.settings_card);
        aboutCard = findViewById(R.id.about_card);
    }

    private void setupUI() {
        welcomeText.setText("Welcome back, " + currentUser.getFullName() + "!");
        userStatsText.setText("Blood Type: " + currentUser.getBloodType() +
                " | Role: " + currentUser.getUserType().toUpperCase());
    }

    private void setupClickListeners() {
        donorCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, DonorActivity.class);
            startActivity(intent);
        });

        recipientCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipientActivity.class);
            startActivity(intent);
        });

        historyCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });

        feedCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        });

        settingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        aboutCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
