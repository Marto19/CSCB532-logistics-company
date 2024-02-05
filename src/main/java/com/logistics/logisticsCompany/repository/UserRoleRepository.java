package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {  // Fix type parameters
    Optional<UserRole> findByUserRole(String userRole);
}
