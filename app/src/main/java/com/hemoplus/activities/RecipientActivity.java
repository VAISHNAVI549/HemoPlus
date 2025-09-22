package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class RecipientActivity extends AppCompatActivity {
    private TextView recipientInfoText, myRequestsText;
    private EditText locationEdit, descriptionEdit;
    private Spinner urgencySpinner, bloodTypeSpinner;
    private Button createRequestButton, backButton, homeButton, historyButton;
    private ListView myRequestsList;
    private CardView requestCard, findDonorsCard, urgentRequestsCard;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getUserSession();

        if (currentUser == null) {
            redirectToSignIn();
            return;
        }

        initializeViews();
        setupUI();
        loadMyRequests();
        setupClickListeners();
    }

    private void initializeViews() {
        recipientInfoText = findViewById(R.id.recipient_info_text);
        myRequestsText = findViewById(R.id.my_requests_text);
        locationEdit = findViewById(R.id.location_edit);
        descriptionEdit = findViewById(R.id.description_edit);
        urgencySpinner = findViewById(R.id.urgency_spinner);
        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        createRequestButton = findViewById(R.id.create_request_button);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        historyButton = findViewById(R.id.history_button);
        myRequestsList = findViewById(R.id.my_requests_list);
        requestCard = findViewById(R.id.request_card);
        findDonorsCard = findViewById(R.id.find_donors_card);
        urgentRequestsCard = findViewById(R.id.urgent_requests_card);
    }

    private void setupUI() {
        recipientInfoText.setText("Blood Type Needed: " + currentUser.getBloodType());
    }

    private void loadMyRequests() {
        List<BloodRequest> requests = dbHelper.getUserBloodRequests(currentUser.getId());
        myRequestsText.setText("My Active Requests: " + requests.size());
    }

    private void setupClickListeners() {
        createRequestButton.setOnClickListener(v -> createBloodRequest());

        findDonorsCard.setOnClickListener(v -> {
            Toast.makeText(this, "Searching for compatible donors...", Toast.LENGTH_SHORT).show();
            // Add search logic here
        });

        urgentRequestsCard.setOnClickListener(v -> {
            Toast.makeText(this, "Showing urgent blood requests...", Toast.LENGTH_SHORT).show();
            // Show urgent requests
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

    private void createBloodRequest() {
        String location = locationEdit.getText().toString().trim();
        String description = descriptionEdit.getText().toString().trim();
        String urgency = urgencySpinner.getSelectedItem().toString();
        String bloodType = bloodTypeSpinner.getSelectedItem().toString();

        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }

        BloodRequest request = new BloodRequest(currentUser.getId(), bloodType, urgency, location, description);
        long result = dbHelper.createBloodRequest(request);

        if (result != -1) {
            Toast.makeText(this, "Blood request created successfully!", Toast.LENGTH_SHORT).show();
            locationEdit.setText("");
            descriptionEdit.setText("");
            loadMyRequests();
        } else {
            Toast.makeText(this, "Failed to create request", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
