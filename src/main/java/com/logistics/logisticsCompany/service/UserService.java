package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User registerUser(User user) {
        // Implement registration logic, e.g., encrypt password
        // Save the user to the database
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        System.out.println("Searching for user with username: " + username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found");
        }
        return user;
    }

    public void assignUserRole(User user, UserRole userRole) {
        user.getUserRoleList().add(userRole);
        userRepository.save(user);
    }
}


