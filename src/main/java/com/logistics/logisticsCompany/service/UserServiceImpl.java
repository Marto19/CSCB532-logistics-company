package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User registerUser(User user) {
        // Implement registration logic, e.g., encrypt password
        // Save the user to the database
        return userRepository.save(user);
    }

    @Override
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

    @Override
    public void assignUserRole(User user, UserRole userRole) {
        user.getUserRoleList().add(userRole);
        userRepository.save(user);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(long userId, User updatedUser) {
        if (userRepository.existsById(userId)) {
            updatedUser.setId(userId);
            userRepository.save(updatedUser);
        } else {
            //TODO: THROW CUSTOM EXCEPTION
        }
    }

    @Override
    public void deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            //TODO: THROW CUSTOM EXCEPTION
        }
    }
}
