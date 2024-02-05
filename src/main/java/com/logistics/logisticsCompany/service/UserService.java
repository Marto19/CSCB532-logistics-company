package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User findUserByUsername(String username);

    void assignUserRole(User user, UserRole userRole);

    void createUser(User user);

    List<User> getAllUsers();

    void updateUser(long userId, User updatedUser);

    void deleteUser(long userId);
}
