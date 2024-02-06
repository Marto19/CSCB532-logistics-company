package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.UserDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public User registerUser(UserDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username already exists.");
        }
        
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        
        //fixme idk how password is encoded -caki
        user.setPassword(registerDTO.getPassword());
        
        return userRepository.save(user);
    }

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
    
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public User getUserById(long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
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
