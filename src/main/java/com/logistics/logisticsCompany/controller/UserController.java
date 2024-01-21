package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import com.logistics.logisticsCompany.service.UserRoleService;
import com.logistics.logisticsCompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;  // Add this line
    @Autowired
    public UserController(UserService userService, UserRoleService userRoleService, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;  // Add this line
        this.userRoleRepository = userRoleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Validate user data
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        // Implement login logic using loginRequest.getUsername() and loginRequest.getPassword()
        // Return a token or session information upon successful login
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }


    @PostMapping("/assign-roles")
    public ResponseEntity<String> assignRoles(@RequestParam String username, @RequestParam Set<String> roles) {
        System.out.println("Assign Roles method reached.");
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Set<UserRole> userRoles = roles.stream()
                .map(userRoleService::findUserRoleByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        // Add roles to the user
        user.getUserRoleList().addAll(userRoles);
        userRepository.save(user);

        // Update user reference in roles
        userRoles.forEach(userRole -> {
            userRole.getUserList().add(user);
            userRoleRepository.save(userRole);
        });
        //TO ASSIGN MULTIPLE ROLES TO A USER WE NEED TO:
        //-ADD THEM IN THE URL QUERY LIKE:api/users/assign-roles?username=xor7&roles=ROLE_USER,ROLE_EMPLOYEE
        //-ADD THEM IN THE RAW BODY THROUGH JSON LIKE:
        //{
        //  "username": "xor7",
        //  "roles": ["ROLE_USER, ROLE_EMPLOYEE"]
        //} // THE QUERY AND THE BODY SHOULD MATCH IN PROPERTIES TODO: think about removing them from the query
        return new ResponseEntity<>("Roles assigned successfully", HttpStatus.OK);
    }

}


