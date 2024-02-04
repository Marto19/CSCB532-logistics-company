package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    Optional<UserRole> findUserRoleByName(String roleName);
    List<UserRole> getAllUserRoles();
    UserRole getUserRoleById(long id);
}
