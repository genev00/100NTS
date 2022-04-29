package com.example.a100nts.data.login;

import com.example.a100nts.entities.User;

public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDataSource loginDataSource;
    private static User loggedUser = null;

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
            loggedUser = ((Result.Success<User>) result).getData();
        }
        return result;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static synchronized void setLoggedUser(User loggedUser) {
        LoginRepository.loggedUser = loggedUser;
    }

}