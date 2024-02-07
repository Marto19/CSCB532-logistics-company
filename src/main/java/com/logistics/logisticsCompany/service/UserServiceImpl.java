package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.UserDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository Repository for managing users.
     * @param userRoleRepository Repository for managing user roles.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Registers a new user.
     *
     * @param userDTO Data transfer object containing the new user's details.
     * @return The newly registered user.
     * @throws DataIntegrityViolationException if a user with the same username already exists.
     */
    @Transactional
    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username already exists.");
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Encode the password
        
        // Parse the userRoleBeingSet to a long
        long userRoleId;
        try {
            userRoleId = Long.parseLong(userDTO.getUserRoleBeingSet());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("UserRole must be a valid number.");
        }
        
        // Fetch the UserRole from the database
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + userRoleId));
        
        // Set the userRoleList
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setUserRoleList(userRoles);
        
        // Save the user
        return userRepository.save(user);
    }
    
    
    
    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The found user, or null if no user was found.
     */
    @Override
    public User findUserByUsername(String username) {
        System.out.println("Searching for user with username: " + username);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found");
        }
        return user;
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to find.
     * @return An Optional containing the found user, or empty if no user was found.
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found user, or empty if no user was found.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Assigns a user role to a user.
     *
     * @param user The user to assign the role to.
     * @param userRole The role to assign to the user.
     */
    @Override
    public void assignUserRole(User user, UserRole userRole) {
        user.getUserRoleList().add(userRole);
        userRepository.save(user);
    }

    /**
     * Creates a new user.
     *
     * @param user The user to create.
     */
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The found user.
     * @throws EntityNotFoundException if no user was found with the provided ID.
     */
    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
    }


    /**
     * Updates a user.
     *
     * @param userId The ID of the user to update.
     * @param updatedUser The updated user.
     */
    @Override
    public void updateUser(long userId, User updatedUser) {
        if (userRepository.existsById(userId)) {
            updatedUser.setId(userId);
            userRepository.save(updatedUser);
        } else {
            //TODO: THROW CUSTOM EXCEPTION
        }
    }

    /**
     * Deletes a user.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    public void deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            //TODO: THROW CUSTOM EXCEPTION
        }
    }
}
