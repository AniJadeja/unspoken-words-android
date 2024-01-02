package com.penofdreams.unspokenwords.activities;

import static com.penofdreams.unspokenwords.firebase.Authentication.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.penofdreams.unspokenwords.databinding.ActivityLoginBinding;
import com.penofdreams.unspokenwords.firebase.Authentication;

public class LoginActivity extends AppCompatActivity implements Authentication.AuthListener {

    private TextInputEditText email, password;
    private String emailText, passwordText;
    private MaterialButton loginButton;
    private ActivityLoginBinding binding;

    private Authentication authentication;
    private static final String TAG = "flow -> LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUserIsLoggedIn();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        email = binding.usernameEditText;
        password = binding.passwordEditText;
        loginButton = binding.loginButton;
        setupViews();
    }

    private void checkIfUserIsLoggedIn() {
        authentication = new Authentication(this);
        if (authentication.isUserLoggedIn()) {
            Log.d(TAG, "checkIfUserIsLoggedIn: User is logged in");
            navigateToMainActivity();
        }
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void setupViews() {
        loginButton.setOnClickListener(v -> {
            emailText = email.getText().toString();
            passwordText = password.getText().toString();
            if (emailText.isEmpty()) {
                email.setError("Email cannot be empty");
            } else if (passwordText.isEmpty()) {
                password.setError("Password cannot be empty");
            } else {
                Log.d(TAG, "setupViews: "); authentication.signIn(emailText, passwordText, this);
            }
        });
    }


    public void onAuthStateChanged(int authState) {
        if (authState == RC_SIGN_IN) navigateToMainActivity();
    }

    @Override
    public void onFailedAuth() {
        Log.d(TAG, "onFailedAuth: Failed to login");
    }
}