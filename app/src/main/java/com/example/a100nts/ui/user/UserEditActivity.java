package com.example.a100nts.ui.user;

import static com.example.a100nts.common.Constants.PASSWORD_REGEX;
import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.data.login.LoginRepository.setLoggedUser;
import static com.example.a100nts.ui.user.UserActivity.bindingCache;
import static com.example.a100nts.utils.ActivityHolder.setActivity;
import static com.example.a100nts.utils.RestService.checkIfEmailExists;
import static com.example.a100nts.utils.RestService.updateUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivityUserEditBinding;
import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;

import java.util.regex.Pattern;

public class UserEditActivity extends AppCompatActivity {

    private ActivityUserEditBinding binding;

    private EditText editName;
    private EditText editSurname;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPassword2;
    private Switch editRankSwitch;

    private String currentName;
    private String currentSurname;
    private String currentEmail;
    private boolean currentRanking;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        editName = binding.editName;
        editSurname = binding.editSurname;
        editEmail = binding.editEmail;
        editPassword = binding.editPassword;
        editPassword2 = binding.editPassword2;
        editRankSwitch = binding.editRankSwitch;
        binding.editUserButton.setEnabled(false);

        setUpButtons();
    }

    private void setUpButtons() {
        loadCurrentInformation();

        TextWatcher editUserTextChangedListener = getEditUserTextChangedListener();
        editName.addTextChangedListener(editUserTextChangedListener);
        editSurname.addTextChangedListener(editUserTextChangedListener);
        editEmail.addTextChangedListener(editUserTextChangedListener);
        editPassword.addTextChangedListener(editUserTextChangedListener);
        editPassword2.addTextChangedListener(editUserTextChangedListener);
        editRankSwitch.setOnClickListener(l -> editUserDataChanged());

        binding.editUserButton.setOnClickListener(l -> {
            UserUI user = updateUser(
                    new User(getLoggedUser().getId(),
                            editName.getText().toString(),
                            editSurname.getText().toString(),
                            editEmail.getText().toString(),
                            editPassword.getText().toString(),
                            editRankSwitch.isChecked())
            );
            if (user == null) {
                finish();
                System.exit(1);
            }
            setLoggedUser(user);
            Toast.makeText(this, getText(R.string.updated_successfully), Toast.LENGTH_SHORT).show();
            bindingCache.firstName.setText(user.getFirstName());
            finish();
        });
    }

    private void loadCurrentInformation() {
        final UserUI user = getLoggedUser();
        currentName = user.getFirstName();
        currentSurname = user.getLastName();
        currentEmail = user.getEmail();
        currentRanking = user.isRanking();

        editName.setText(currentName);
        editSurname.setText(currentSurname);
        editEmail.setText(currentEmail);
        editRankSwitch.setChecked(currentRanking);
    }

    @NonNull
    private TextWatcher getEditUserTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editUserDataChanged();
            }
        };
    }

    private void editUserDataChanged() {
        binding.editUserButton.setEnabled(false);

        final String newName = editName.getText().toString();
        final String newSurname = editSurname.getText().toString();
        final String newEmail = editEmail.getText().toString();
        final String newPassword = editPassword.getText().toString();
        final String newPassword2 = editPassword2.getText().toString();
        final boolean newRanking = editRankSwitch.isChecked();


        if (newName.length() < 3 || Pattern.compile("[^a-zA-Zа-яА-Я]").matcher(newName).find()) {
            editName.setError(getString(R.string.invalid_name));
            return;
        }

        if (newSurname.length() < 3 || Pattern.compile("[^a-zA-Zа-яА-Я]").matcher(newSurname).find()) {
            editSurname.setError(getString(R.string.invalid_surname));
            return;
        }

        if (!newEmail.equals(currentEmail)) {
            if (newEmail.contains("abv.bg") || newEmail.contains("mail.bg")
                     || !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                editEmail.setError(getString(R.string.invalid_email));
                return;
            }

            Boolean emailExists = checkIfEmailExists(newEmail);
            if (emailExists == null) {
                finish();
                System.exit(1);
            }
            if (emailExists) {
                editEmail.setError(getString(R.string.existing_email));
                return;
            }
        }

        if (!newPassword.isEmpty()) {
            if (!Pattern.compile(PASSWORD_REGEX).matcher(newPassword).find()) {
                editPassword.setError(getString(R.string.invalid_password));
                return;
            }
            if (!newPassword.equals(newPassword2)) {
                editPassword2.setError(getString(R.string.nonmatching_passwords));
                return;
            }
        }

        if (!newName.equals(currentName) || !newSurname.equals(currentSurname)
            || !newEmail.equals(currentEmail) || !newPassword.isEmpty()
            || newRanking != currentRanking) {
            binding.editUserButton.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
