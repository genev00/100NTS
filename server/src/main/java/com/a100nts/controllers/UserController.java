package com.a100nts.controllers;

import com.a100nts.models.User;
import com.a100nts.repositories.UserRepository;
import com.a100nts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/emailExists/{email}")
    public ResponseEntity<Boolean> doesEmailExist(@PathVariable String email) {
        return new ResponseEntity<>(userRepository.findByEmail(email).isPresent(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User internalUser = userRepository.findByEmail(user.getEmail()).get();
        if (!passwordEncoder.matches(user.getPassword(), internalUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        internalUser.setPassword("");
        return new ResponseEntity<>(internalUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        newUser.setPassword("");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
