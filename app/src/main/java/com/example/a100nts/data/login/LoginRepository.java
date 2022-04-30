package com.example.a100nts.data.login;

import com.example.a100nts.entities.UserUI;

public class LoginRepository {

    private static volatile LoginRepository instance;
    private static UserUI loggedUser = null;

    private final LoginDataSource loginDataSource;

    private LoginRepository(LoginDataSource loginDataSource) {
        this.loginDataSource = loginDataSource;
    }

    public static LoginRepository getInstance(LoginDataSource loginDataSource) {
        if (instance == null) {
            instance = new LoginRepository(loginDataSource);
        }
        return instance;
    }

    public void logout() {
        loggedUser = null;
    }

    public Result login(String email, String password) {
        Result result = loginDataSource.login(email, password);
        if (result instanceof Result.Success) {
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

}