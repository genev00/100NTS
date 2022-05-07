package com.example.a100nts.ui.login;

import static android.util.Patterns.EMAIL_ADDRESS;
import static com.example.a100nts.common.Constants.PASSWORD_REGEX;
import static com.example.a100nts.utils.ActivityHolder.getActivity;
import static com.example.a100nts.data.login.LoginRepository.isUserLogged;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.util.Patterns;

import com.example.a100nts.common.Constants;
import com.example.a100nts.data.login.LoginRepository;
import com.example.a100nts.data.login.Result;
import com.example.a100nts.R;
import com.example.a100nts.ui.user.UserActivity;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

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
        } else if (isUserLogged()) {
            Intent loggedIntent = new Intent(getActivity(), UserActivity.class);
            getActivity().startActivity(loggedIntent);
            getActivity().finish();
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
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
        return !email.contains("abv.bg") && !email.contains("mail.bg")
                && EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && Pattern.compile(PASSWORD_REGEX).matcher(password).find();
    }

}