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

public class SignInActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit;
    private Button signInButton;
    private TextView signUpLink, forgotPasswordLink, aboutLink;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeViews();
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        setupClickListeners();
    }

    private void initializeViews() {
        emailEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.password_edit);
        signInButton = findViewById(R.id.sign_in_button);
        signUpLink = findViewById(R.id.sign_up_link);
        forgotPasswordLink = findViewById(R.id.forgot_password_link);
        aboutLink = findViewById(R.id.about_link);
    }

    private void setupClickListeners() {
        signInButton.setOnClickListener(v -> performSignIn());

        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPasswordLink.setOnClickListener(v -> {
            Toast.makeText(this, "Forgot Password feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        aboutLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void performSignIn() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = dbHelper.authenticateUser(email, password);
        if (user != null) {
            sessionManager.saveUserSession(user);
            Toast.makeText(this, "Welcome back, " + user.getFullName() + "!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
