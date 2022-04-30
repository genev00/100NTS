package com.example.a100nts.ui.register;

import static com.example.a100nts.common.RestUtil.REST_TEMPLATE;
import static com.example.a100nts.common.RestUtil.SERVER_URL;
import static com.example.a100nts.common.StringProvider.setContext;
import static com.example.a100nts.data.login.LoginRepository.setLoggedUser;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivityRegisterBinding;
import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        setContext(this);

        binding.regEmail.setText(email);
        binding.regEmail.setEnabled(false);
        binding.regPassword.setText(password);
        binding.regPassword.setEnabled(false);

        final EditText name = binding.regName;
        final EditText surname = binding.regSurname;
        final EditText password2 = binding.regPassword2;
        final Button regButton = binding.regButton;
        regButton.setEnabled(false);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginDataChanged(name, surname, password2, regButton);
            }
        };
        name.addTextChangedListener(afterTextChangedListener);
        surname.addTextChangedListener(afterTextChangedListener);
        password2.addTextChangedListener(afterTextChangedListener);

        regButton.setOnClickListener(v -> registerUser(
                name.getText().toString(),
                surname.getText().toString(),
                binding.rankSwitch.isChecked()
        ));
    }

    private void loginDataChanged(EditText name, EditText surname, EditText password2, Button regButton) {
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
        HttpEntity<User> user = new HttpEntity<>(
                new User(name, surname, email, password, ranking)
        );
        ResponseEntity<? extends UserUI> result = REST_TEMPLATE.exchange(
                SERVER_URL + "/register", HttpMethod.POST, user, UserUI.class
        );
        if (result.getStatusCode() == HttpStatus.CREATED) {
            setLoggedUser(result.getBody());
        }
    }

    public static void setRegistrationData(String email, String password) {
        RegisterActivity.email = email;
        RegisterActivity.password = password;
    }

}
