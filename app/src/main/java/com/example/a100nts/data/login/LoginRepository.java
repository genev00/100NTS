package com.example.a100nts.data.login;

import static com.example.a100nts.utils.ActivityHolder.getActivity;
import static com.example.a100nts.utils.RestService.checkIfEmailExists;
import static com.example.a100nts.utils.RestService.loginUser;
import static com.example.a100nts.ui.register.RegisterActivity.setRegistrationData;

import android.content.Intent;

import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.ui.register.RegisterActivity;

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

    @SuppressWarnings("unchecked")
    public Result login(String email, String password) {
        logout();
        Result result = restLogin(email, password);
        if (result instanceof Result.Success && ((Result.Success<?>) result).getData() != null) {
            loggedUser = ((Result.Success<UserUI>) result).getData();
        }
        return result;
    }

    public void logout() {
        loggedUser = null;
    }

    public static boolean isUserLogged() {
        return loggedUser != null;
    }

    public static UserUI getLoggedUser() {
        return loggedUser;
    }

    public static synchronized void setLoggedUser(UserUI loggedUser) {
        LoginRepository.loggedUser = loggedUser;
    }

    public static Result restLogin(String email, String password) {
        Boolean emailExists = checkIfEmailExists(email);
        if (emailExists == null) {
            getActivity().finish();
            System.exit(1);
        }
        if (!emailExists) {
            Intent register = new Intent(getActivity(), RegisterActivity.class);
            setRegistrationData(email, password);
            getActivity().startActivity(register);
            return new Result.Success<>(null);
        }
        Object loginResult = loginUser(new User(email, password));
        if (loginResult == null) {
            getActivity().finish();
            System.exit(1);
        }
        if (loginResult instanceof UserUI) {
            return new Result.Success<>((UserUI) loginResult);
        } else {
            return new Result.Error(new IOException((String) loginResult));
        }
    }

}