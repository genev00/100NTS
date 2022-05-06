package com.a100nts.controllers;

import com.a100nts.dto.UserDTO;
import com.a100nts.models.User;
import com.a100nts.repositories.UserRepository;
import com.a100nts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.a100nts.utils.UserMapper.userToDTO;
import static com.a100nts.utils.UserMapper.usersToDTOs;

@RestController
@RequestMapping("/api/v1/users")
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
        return new ResponseEntity<>(userToDTO(internalUser), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userToDTO(userService.registerUser(user)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(usersToDTOs(userService.getAllUsers()), HttpStatus.OK);
    }

}
