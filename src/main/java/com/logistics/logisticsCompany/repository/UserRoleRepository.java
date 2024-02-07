package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for UserRole entities.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {  // Fix type parameters
    /**
     * Check if a user role exists with the given role name.
     *
     * @param userRole The role name of the user to check for existence.
     * @return True if a user role with the given role name exists, false otherwise.
     */
    Optional<UserRole> findByUserRole(String userRole);

    /**
     * Check if a user role exists with the given ID.
     *
     * @param userRole The ID of the user role to check for existence.
     * @return True if a user role with the given ID exists, false otherwise.
     */
    UserRole findUserRoleById(long userRole);
    

}
