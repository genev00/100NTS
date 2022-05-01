package com.example.a100nts.ui.login;

import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.ui.login.LoginActivity.loginActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.util.Patterns;

import com.example.a100nts.data.login.LoginRepository;
import com.example.a100nts.data.login.Result;
import com.example.a100nts.R;
import com.example.a100nts.ui.user.UserActivity;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

    private static final String PASSWORD_REGEX = "^(?=[^A-Z\\n]*[A-Z])(?=[^a-z\\n]*[a-z])(?=[^0-9\\n]*[0-9])(?=[^#?!@$%^&*\\n-]*[#?!@$%^&*-]).{8,}$";

    private final MutableLiveData<LoginFormState> loginFormState;
    private final MutableLiveData<LoginError> loginResult;
    private final LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        this.loginFormState = new MutableLiveData<>();
        this.loginResult = new MutableLiveData<>();
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginError> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        Result result = loginRepository.login(username, password);
        if (result instanceof Result.Error) {
            loginResult.setValue(new LoginError(((Result.Error) result).getError().getMessage()));
        } else if (getLoggedUser() != null) {
            Intent loggedIntent = new Intent(loginActivity, UserActivity.class);
            loginActivity.startActivity(loggedIntent);
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return !email.contains("abv.bg") && !email.contains("mail.bg") &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && Pattern.compile(PASSWORD_REGEX).matcher(password).find();
    }

}