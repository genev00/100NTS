package com.a100nts.services;

import com.a100nts.models.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);
	List<User> getAllUsers();
	User updateUser(User user);

}
