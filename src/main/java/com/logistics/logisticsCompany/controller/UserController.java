package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Validate user data
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        // Implement login logic using loginRequest.getUsername() and loginRequest.getPassword()
        // Return a token or session information upon successful login
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }
}


