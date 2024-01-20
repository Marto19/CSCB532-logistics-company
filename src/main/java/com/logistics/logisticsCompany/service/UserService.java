package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Implement registration logic, e.g., encrypt password
        // Save the user to the database
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


