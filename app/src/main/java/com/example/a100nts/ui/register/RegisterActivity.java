package com.example.a100nts.ui.register;

import static com.example.a100nts.utils.ActivityHolder.setActivity;
import static com.example.a100nts.data.login.LoginRepository.setLoggedUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.utils.RestService;
import com.example.a100nts.databinding.ActivityRegisterBinding;
import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.ui.user.UserActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static String email;
    private static String password;
    private ActivityRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        setUpButtons();
    }

    private void setUpButtons() {
        binding.regEmail.setText(email);
        binding.regEmail.setEnabled(false);
        binding.regPassword.setText(password);
        binding.regPassword.setEnabled(false);

        final EditText name = binding.regName;
        final EditText surname = binding.regSurname;
        final EditText password2 = binding.regPassword2;
        final Button regButton = binding.regButton;
        regButton.setEnabled(false);

        TextWatcher getRegisterTextChangedListener = getRegisterTextChangedListener(name, surname, password2, regButton);
        name.addTextChangedListener(getRegisterTextChangedListener);
        surname.addTextChangedListener(getRegisterTextChangedListener);
        password2.addTextChangedListener(getRegisterTextChangedListener);
        password2.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && regButton.isEnabled()) {
                registerUser(
                        name.getText().toString(),
                        surname.getText().toString(),
                        binding.rankSwitch.isChecked()
                );
            }
            return false;
        });

        regButton.setOnClickListener(v -> registerUser(
                name.getText().toString(),
                surname.getText().toString(),
                binding.rankSwitch.isChecked()
        ));
    }

    @NonNull
    private TextWatcher getRegisterTextChangedListener(EditText name, EditText surname, EditText password2, Button regButton) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerDataChanged(name, surname, password2, regButton);
            }
        };
    }

    private void registerDataChanged(EditText name, EditText surname, EditText password2, Button regButton) {
        regButton.setEnabled(false);

        String nameS = name.getText().toString();
        String surnameS = surname.getText().toString();
        String passwordS = password2.getText().toString();

        if (nameS.length() < 3 || Pattern.compile("[^a-zA-Zа-яА-Я]").matcher(nameS).find()) {
            name.setError(getString(R.string.invalid_name));
            return;
        }

        if (surnameS.length() < 3 || Pattern.compile("[^a-zA-Zа-яА-Я]").matcher(surnameS).find()) {
            surname.setError(getString(R.string.invalid_surname));
            return;
        }

        if (!passwordS.equals(password)) {
            password2.setError(getString(R.string.nonmatching_passwords));
            return;
        }

        regButton.setEnabled(true);
    }

    private void registerUser(String name, String surname, boolean ranking) {
        UserUI user = RestService.registerUser(
                new User(name, surname, email, password, ranking)
        );
        if (user == null) {
            finish();
            System.exit(1);
        }
        setLoggedUser(user);
        Intent loggedIntent = new Intent(this, UserActivity.class);
        startActivity(loggedIntent);
        finish();
    }

    public static void setRegistrationData(String email, String password) {
        RegisterActivity.email = email;
        RegisterActivity.password = password;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
