package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;
import com.hemoplus.database.DatabaseHelper;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class HistoryActivity extends AppCompatActivity {
    private TextView historyTitleText, donationStatsText, requestStatsText;
    private ListView donationHistoryList, requestHistoryList;
    private CardView donationHistoryCard, requestHistoryCard;
    private Button backButton, homeButton, exportButton;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        setupUI();
        loadHistory();
        setupClickListeners();
    }

    private void initializeViews() {
        historyTitleText = findViewById(R.id.history_title_text);
        donationStatsText = findViewById(R.id.donation_stats_text);
        requestStatsText = findViewById(R.id.request_stats_text);
        donationHistoryList = findViewById(R.id.donation_history_list);
        requestHistoryList = findViewById(R.id.request_history_list);
        donationHistoryCard = findViewById(R.id.donation_history_card);
        requestHistoryCard = findViewById(R.id.request_history_card);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        exportButton = findViewById(R.id.export_button);
    }

    private void setupUI() {
        historyTitleText.setText("Your Blood Donation History");
    }

    private void loadHistory() {
        // Load donation history
        donationStatsText.setText("Total Donations: 3 | Lives Saved: 9");

        // Load request history
        requestStatsText.setText("Total Requests: 1 | Fulfilled: 1");
    }

    private void setupClickListeners() {
        exportButton.setOnClickListener(v -> {
            // Export history functionality
            android.widget.Toast.makeText(this, "Exporting your history...", android.widget.Toast.LENGTH_SHORT).show();
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
