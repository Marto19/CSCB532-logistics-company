package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service for managing user roles.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    /**
     * Constructs a new UserRoleServiceImpl with the given UserRoleRepository.
     *
     * @param userRoleRepository The repository for user roles.
     */
    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    /**
     * Finds a user role by name.
     *
     * @param roleName The name of the role.
     * @return An Optional containing the found user role, or an empty Optional if no role was found.
     */
    @Override
    public Optional<UserRole> findUserRoleByName(String roleName) {
        return userRoleRepository.findByUserRole(roleName);
    }

    /**
     * Retrieves all user roles.
     *
     * @return A list of all user roles.
     */
    @Override
    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    /**
     * Retrieves a user role by ID.
     *
     * @param id The ID of the user role.
     * @return The found user role, or null if no role was found.
     */
    @Override
    public UserRole getUserRoleById(long id) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(id);
        return userRoleOptional.orElse(null);
    }
}
