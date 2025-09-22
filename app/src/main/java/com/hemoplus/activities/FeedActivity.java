package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class FeedActivity extends AppCompatActivity {
    private TextView feedTitleText, urgentAlertsText;
    private ListView feedList, urgentAlertsList;
    private CardView emergencyAlertsCard, communityUpdatesCard, successStoriesCard;
    private Button backButton, homeButton, refreshButton;

    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        setupUI();
        loadFeed();
        setupClickListeners();
    }

    private void initializeViews() {
        feedTitleText = findViewById(R.id.feed_title_text);
        urgentAlertsText = findViewById(R.id.urgent_alerts_text);
        feedList = findViewById(R.id.feed_list);
        urgentAlertsList = findViewById(R.id.urgent_alerts_list);
        emergencyAlertsCard = findViewById(R.id.emergency_alerts_card);
        communityUpdatesCard = findViewById(R.id.community_updates_card);
        successStoriesCard = findViewById(R.id.success_stories_card);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        refreshButton = findViewById(R.id.refresh_button);
    }

    private void setupUI() {
        feedTitleText.setText("Blood Donation Feed");
        urgentAlertsText.setText("🚨 2 Urgent blood requests in your area");
    }

    private void loadFeed() {
        // Simulate loading feed content
        android.widget.Toast.makeText(this, "Loading latest updates...", android.widget.Toast.LENGTH_SHORT).show();
    }

    private void setupClickListeners() {
        emergencyAlertsCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Showing emergency alerts...", android.widget.Toast.LENGTH_SHORT).show();
        });

        communityUpdatesCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Loading community updates...", android.widget.Toast.LENGTH_SHORT).show();
        });

        successStoriesCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Reading success stories...", android.widget.Toast.LENGTH_SHORT).show();
        });

        refreshButton.setOnClickListener(v -> {
            loadFeed();
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
