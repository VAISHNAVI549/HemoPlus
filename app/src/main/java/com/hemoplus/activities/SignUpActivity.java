package com.hemoplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hemoplus.R;
import com.hemoplus.database.DatabaseHelper;
import com.hemoplus.models.User;
import com.hemoplus.utils.SessionManager;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit, confirmPasswordEdit, fullNameEdit, phoneEdit;
    private Spinner bloodTypeSpinner;
    private RadioGroup userTypeGroup;
    private Button signUpButton;
    private TextView signInLink, aboutLink;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        setupClickListeners();
    }

    private void initializeViews() {
        emailEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.password_edit);
        confirmPasswordEdit = findViewById(R.id.confirm_password_edit);
        fullNameEdit = findViewById(R.id.full_name_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        userTypeGroup = findViewById(R.id.user_type_group);
        signUpButton = findViewById(R.id.sign_up_button);
        signInLink = findViewById(R.id.sign_in_link);
        aboutLink = findViewById(R.id.about_link);
    }

    private void setupClickListeners() {
        signUpButton.setOnClickListener(v -> performSignUp());

        signInLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        aboutLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void performSignUp() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String confirmPassword = confirmPasswordEdit.getText().toString().trim();
        String fullName = fullNameEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();
        String bloodType = bloodTypeSpinner.getSelectedItem().toString();

        int selectedUserTypeId = userTypeGroup.getCheckedRadioButtonId();
        if (selectedUserTypeId == -1) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedUserType = findViewById(selectedUserTypeId);
        String userType = selectedUserType.getText().toString().toLowerCase();

        // Validation
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(email, password, fullName, phone, bloodType, userType);
        long result = dbHelper.registerUser(newUser);

        if (result != -1) {
            newUser.setId((int) result);
            sessionManager.saveUserSession(newUser);
            Toast.makeText(this, "Registration successful! Welcome " + fullName + "!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Email may already exist.", Toast.LENGTH_SHORT).show();
        }
    }
}