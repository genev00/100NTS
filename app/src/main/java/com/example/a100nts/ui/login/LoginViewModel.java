package com.example.a100nts.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.a100nts.data.login.LoginRepository;
import com.example.a100nts.data.login.Result;
import com.example.a100nts.R;

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
        return password != null && password.trim().length() > 5;
    }

}