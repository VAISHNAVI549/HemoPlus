package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hemoplus.R;
import com.hemoplus.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000;
    private ImageView logoImage;
    private TextView appNameText, taglineText;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeViews();
        sessionManager = new SessionManager(this);

        animateLogo();
        navigateToNextScreen();
    }

    private void initializeViews() {
        logoImage = findViewById(R.id.logo_image);
        appNameText = findViewById(R.id.app_name_text);
        taglineText = findViewById(R.id.tagline_text);
    }

    private void animateLogo() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        logoImage.startAnimation(scaleAnimation);

        // Fade in text
        appNameText.setAlpha(0f);
        taglineText.setAlpha(0f);

        appNameText.animate().alpha(1f).setDuration(1000).setStartDelay(800).start();
        taglineText.animate().alpha(1f).setDuration(1000).setStartDelay(1500).start();
    }

    private void navigateToNextScreen() {
        new Handler().postDelayed(() -> {
            Intent intent;
            if (sessionManager.isLoggedIn()) {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, SignInActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
