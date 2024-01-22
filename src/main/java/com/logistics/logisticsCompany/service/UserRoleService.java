package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.enums.UserRole;

import java.util.Optional;

public interface UserRoleService {

    Optional<UserRole> findUserRoleByName(String roleName);
}
