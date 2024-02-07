package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is a controller for handling requests related to UserRole.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/user-roles")
public class UserRoleController {

    /**
     * The UserRoleService instance used for user role-related operations.
     */
    private final UserRoleService userRoleService;

    /**
     * Constructs a UserRoleController with the specified UserRoleService.
     * @param userRoleService the UserRoleService
     */
    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * This method handles the GET requests for getting all user roles.
     * @return a list of UserRole
     */
    @GetMapping
    public List<UserRole> getAllUserRoles() {
        return userRoleService.getAllUserRoles();
    }

    /**
     * This method handles the GET requests for getting a user role by id.
     * @param id the id of the user role
     * @return a ResponseEntity with the UserRole if it exists, or a not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable long id) {
        UserRole userRole = userRoleService.getUserRoleById(id);
        if (userRole != null) {
            return ResponseEntity.ok(userRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}