package com.a100nts.services;

import com.a100nts.models.User;

public interface UserService {

    User registerUser(User user);
    User findUserById(Long id);
}
