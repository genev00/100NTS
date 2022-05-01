package com.example.a100nts.data.login;

import static com.example.a100nts.common.RestUtil.OBJECT_MAPPER;
import static com.example.a100nts.common.RestUtil.REST_TEMPLATE;
import static com.example.a100nts.common.RestUtil.SERVER_URL;
import static com.example.a100nts.common.StringProvider.getTranslatedString;
import static com.example.a100nts.ui.login.LoginActivity.loginActivity;
import static com.example.a100nts.ui.register.RegisterActivity.setRegistrationData;

import android.content.Intent;

import com.example.a100nts.R;
import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.ui.register.RegisterActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRepository {

    private static volatile LoginRepository instance;
    private static UserUI loggedUser;

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void logout() {
        loggedUser = null;
    }

    @SuppressWarnings("unchecked")
    public Result login(String email, String password) {
        Result result = restLogin(email, password);
        if (result instanceof Result.Success && ((Result.Success<?>) result).getData() != null) {
            loggedUser = ((Result.Success<UserUI>) result).getData();
        }
        return result;
    }

    public static UserUI getLoggedUser() {
        return loggedUser;
    }

    public static synchronized void setLoggedUser(UserUI loggedUser) {
        LoginRepository.loggedUser = loggedUser;
    }

    public static Result restLogin(String email, String password) {
        try {
            ResponseEntity<? extends Boolean> checkEmailResult = REST_TEMPLATE.getForEntity(
                    SERVER_URL + "/emailExists" + '/' + email, Boolean.class
            );
            if (checkEmailResult.getStatusCode() == HttpStatus.OK && !checkEmailResult.getBody()) {
                Intent register = new Intent(loginActivity, RegisterActivity.class);
                setRegistrationData(email, password);
                loginActivity.startActivity(register);
                return new Result.Success<>(null);
            }

            HttpEntity<User> user = new HttpEntity<>(new User(email, password));
            ResponseEntity<?> result = REST_TEMPLATE.exchange(
                    SERVER_URL + "/login", HttpMethod.POST, user, Object.class
            );

            if (result.getStatusCode() == HttpStatus.OK) {
                return new Result.Success<>(OBJECT_MAPPER.convertValue(result.getBody(), UserUI.class));
            } else {
                return new Result.Error(new IOException((String) result.getBody()));
            }
        } catch (Exception e) {
            if (e.getMessage() != null &&
                    e.getMessage().contains(HttpStatus.FORBIDDEN.toString())) {
                return new Result.Error(new IOException(getTranslatedString(R.string.wrong_password)));
            }
            return new Result.Error(new IOException(getTranslatedString(R.string.login_failed) + ' ' + e.getMessage(), e));
        }
    }

}