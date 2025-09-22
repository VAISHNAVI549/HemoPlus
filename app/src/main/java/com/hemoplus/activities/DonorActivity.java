package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;
import com.hemoplus.database.DatabaseHelper;
import com.hemoplus.models.BloodRequest;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

import java.util.List;

public class DonorActivity extends AppCompatActivity {
    private TextView donorInfoText, availableRequestsText;
    private ListView requestsList;
    private CardView donateNowCard, myDonationsCard, findRecipientsCard;
    private Button backButton, homeButton, historyButton;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        setupUI();
        loadBloodRequests();
        setupClickListeners();
    }

    private void initializeViews() {
        donorInfoText = findViewById(R.id.donor_info_text);
        availableRequestsText = findViewById(R.id.available_requests_text);
        requestsList = findViewById(R.id.requests_list);
        donateNowCard = findViewById(R.id.donate_now_card);
        myDonationsCard = findViewById(R.id.my_donations_card);
        findRecipientsCard = findViewById(R.id.find_recipients_card);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        historyButton = findViewById(R.id.history_button);
    }

    private void setupUI() {
        donorInfoText.setText("Blood Type: " + currentUser.getBloodType() +
                "\nStatus: Available for donation");
    }

    private void loadBloodRequests() {
        List<BloodRequest> requests = dbHelper.getCompatibleBloodRequests(currentUser.getBloodType());
        availableRequestsText.setText("Available Requests: " + requests.size());

        if (requests.size() > 0) {
            Toast.makeText(this, "Found " + requests.size() + " compatible requests", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No compatible requests at the moment", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupClickListeners() {
        donateNowCard.setOnClickListener(v -> {
            Toast.makeText(this, "Connecting you with recipients...", Toast.LENGTH_SHORT).show();
            // Add donation logic here
        });

        myDonationsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });

        findRecipientsCard.setOnClickListener(v -> {
            Toast.makeText(this, "Searching for recipients in your area...", Toast.LENGTH_SHORT).show();
            // Add search logic here
        });

        backButton.setOnClickListener(v -> onBackPressed());

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
