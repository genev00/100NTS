package com.a100nts.controllers;

import com.a100nts.exceptions.UserNotFoundException;
import com.a100nts.models.User;
import com.a100nts.repositories.UserRepository;
import com.a100nts.request.LoginRequest;
import com.a100nts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/emailExists")
    public ResponseEntity<Boolean> doesEmailExist(@PathVariable String email) {
        return new ResponseEntity<>(userRepository.findByEmail(email).isPresent(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user;
        try {
            user = userRepository.findByEmail(loginRequest.getEmail()).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException();
        }


        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<String>("Password is incorrect", HttpStatus.NOT_FOUND);
        }

        user.setPassword("");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);

        newUser.setPassword("");

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
