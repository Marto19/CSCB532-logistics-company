package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Check if a user exists with the given username.
     *
     * @param username The username of the user to check for existence.
     * @return True if a user with the given username exists, false otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds if a user exists with the given username.
     *
     * @param username the username of the user to check for existence.
     * @return True if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user exists with the given id.
     *
     * @param id The id of the user to check for existence.
     * @return True if a user with the given id exists, false otherwise.
     */
    Optional<User> getUserById(long id);
    

    
    
}
