package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hemoplus.R;

public class AboutActivity extends AppCompatActivity {
    private TextView aboutTitleText, versionText, descriptionText, teamText;
    private CardView missionCard, contactCard, faqCard;
    private Button backButton, homeButton, contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initializeViews();
        setupUI();
        setupClickListeners();
    }

    private void initializeViews() {
        aboutTitleText = findViewById(R.id.about_title_text);
        versionText = findViewById(R.id.version_text);
        descriptionText = findViewById(R.id.description_text);
        teamText = findViewById(R.id.team_text);
        missionCard = findViewById(R.id.mission_card);
        contactCard = findViewById(R.id.contact_card);
        faqCard = findViewById(R.id.faq_card);
        backButton = findViewById(R.id.back_button);
        homeButton = findViewById(R.id.home_button);
        contactButton = findViewById(R.id.contact_button);
    }

    private void setupUI() {
        aboutTitleText.setText("About HemoPlus");
        versionText.setText("Version 1.0.0");
        descriptionText.setText("HemoPlus is a revolutionary blood donation platform that connects donors with recipients, saving lives through technology and community collaboration.");
        teamText.setText("Developed with ❤️ by the HemoPlus Team\n\nConnecting Lives Through Blood Donation");
    }

    private void setupClickListeners() {
        contactButton.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Contact: hemoplus@blooddonation.com", android.widget.Toast.LENGTH_LONG).show();
        });

        missionCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Our mission: Save lives through efficient blood donation", android.widget.Toast.LENGTH_SHORT).show();
        });

        contactCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Email: support@hemoplus.com\nPhone: +1-800-HEMO-PLUS", android.widget.Toast.LENGTH_LONG).show();
        });

        faqCard.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "FAQ section coming soon!", android.widget.Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> onBackPressed());

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
