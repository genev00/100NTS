package com.example.a100nts.ui.login;

import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a100nts.databinding.ActivityLoginBinding;
import com.example.a100nts.ui.sites.SitesActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        emailEditText = binding.email;
        passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button viewAllButton = binding.viewAllSitesCut;

        loginViewModel.getLoginFormState().observe(this, getLoginFormStateObserver(loginButton));
        loginViewModel.getLoginResult().observe(this, getLoginErrorObserver());

        setUpButtons(loginButton, viewAllButton);
    }

    private void setUpButtons(Button loginButton, Button viewAllButton) {
        TextWatcher loginTextChangedListener = getLoginTextChangedListener();
        emailEditText.addTextChangedListener(loginTextChangedListener);
        passwordEditText.addTextChangedListener(loginTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && loginButton.isEnabled()) {
                loginViewModel.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
                emailEditText.setText("");
                passwordEditText.setText("");
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loginViewModel.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
            emailEditText.setText("");
            passwordEditText.setText("");
        });
        viewAllButton.setOnClickListener(v -> {
            Intent viewAllSites = new Intent(this, SitesActivity.class);
            startActivity(viewAllSites);
            emailEditText.setText("");
            passwordEditText.setText("");
        });
    }

    @NonNull
    private Observer<LoginError> getLoginErrorObserver() {
        return loginResult -> {
            if (loginResult == null) {
                return;
            }
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
                return;
            }
            setResult(Activity.RESULT_OK);
        };
    }

    @NonNull
    private Observer<LoginFormState> getLoginFormStateObserver(Button loginButton) {
        return loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                emailEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        };
    }

    private TextWatcher getLoginTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };
    }

    private void showLoginFailed(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}