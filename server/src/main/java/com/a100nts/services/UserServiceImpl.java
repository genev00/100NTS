package com.a100nts.services;

import com.a100nts.models.User;
import com.a100nts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
