package com.a100nts.services.impl;

import com.a100nts.models.User;
import com.a100nts.repositories.UserRepository;
import com.a100nts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        return saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User userDB = userRepository.findById(user.getId()).get();
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setEmail(user.getEmail());
        if (!user.getPassword().isBlank()) {
            userDB.setPassword(user.getPassword());
        }
        userDB.setRanking(user.isRanking());
        return saveUser(userDB);
    }

    private User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
