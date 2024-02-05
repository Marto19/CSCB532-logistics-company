package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    Optional<User> getUserById(long id);
}
