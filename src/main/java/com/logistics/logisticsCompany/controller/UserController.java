package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import com.logistics.logisticsCompany.DTO.UserDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import com.logistics.logisticsCompany.service.UserRoleServiceImpl;
import com.logistics.logisticsCompany.service.UserService;
import com.logistics.logisticsCompany.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.Valid;


/**
 * The UserController class handles HTTP requests related to user operations.
 * It provides endpoints for registering, logging in, and managing users.
 */
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRoleServiceImpl userRoleService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EntityDtoMapper entityDtoMapper;


     /**
      * Constructs a UserController with the specified dependencies.
      *
      * @param userServiceImpl The service for handling user-related operations.
      * @param userRoleService The service for handling user role-related operations.
      * @param userRepository The repository for accessing user data.
      * @param userRoleRepository The repository for accessing user role data.
      * @param entityDtoMapper The mapper for converting between entities and DTOs.
      */

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, UserRoleServiceImpl userRoleService, UserRepository userRepository, UserRoleRepository userRoleRepository, EntityDtoMapper entityDtoMapper) {
        this.userService = userServiceImpl;
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.entityDtoMapper = entityDtoMapper;
    }


    /**
     * Handles the HTTP POST request to register a new user.
     *
     * @param userDTO The user to register.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
            User registeredUser = userService.registerUser(userDTO); // Adjust your service method accordingly
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully with ID: " + registeredUser.getId());
            
    }

    /**
     * Handles the HTTP POST request to log in a user.
     *
     * @param loginRequest The user to log in.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        // Implement login logic using loginRequest.getUsername() and loginRequest.getPassword()
        // Return a token or session information upon successful login
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }
    

    /**
     * Handles the HTTP GET request to show the login page.
     * @return A ModelAndView containing the login page.
     */
    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }


//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "index"; // Assuming you have a login.html page
//    }

    /**
     * Handles the HTTP POST request to assign roles to a user.
     *
     * @param username The username of the user to assign roles to.
     * @param roles    The roles to assign to the user.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/assign-roles")
    public ResponseEntity<String> assignRoles(@RequestParam String username, @RequestParam Set<String> roles) {
        System.out.println("Assign Roles method reached.");
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
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

    //////////////////////CRUD OPERATIONS 3. b)///////////

    /**
     * Handles the HTTP POST request to create a new user.
     * @param user The user to create.
     * @return
     * A ResponseEntity indicating the result of the operation.
     * If the username already exists, return a BAD_REQUEST status with a message "Username already exists".
     * If the user is created successfully, return a CREATED status with a message "User created successfully".
     * If an unexpected error occurs, return an INTERNAL_SERVER_ERROR status with a message "An unexpected error occurred: " followed by the error message.
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Check if the username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        // If username doesn't exist, proceed with saving the user
        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }   //todo: add protection in other places too


    /**
     * Handles the HTTP GET request to retrieve all users.
     *
     *
     * @return
     * A ResponseEntity containing the list of users or a NO_CONTENT status if the list is empty.
     * If the list is not empty, return an OK status with the list of users.
     * If an unexpected error occurs, return an INTERNAL_SERVER_ERROR status with a message "An unexpected error occurred: " followed by the error message.
     * If the list is empty, return a NO_CONTENT status.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> registeredUsers = userService.getAllUsers();
        if (registeredUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDTO> userDTOs = registeredUsers.stream()
                .map(entityDtoMapper::convertToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    /**
     * Handles the HTTP GET request to retrieve a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     *               If the user with the provided ID doesn't exist, return a NOT_FOUND status.
     *               If the user with the provided ID exists, return an OK status with the user.
     *               If an unexpected error occurs, return an INTERNAL_SERVER_ERROR status with a message "An unexpected error occurred: " followed by the error message.
     * @return
     * A ResponseEntity containing the user or a NOT_FOUND status if the user doesn't exist.
     * If the user exists, return an OK status with the user.
     * If an unexpected error occurs, return an INTERNAL_SERVER_ERROR status with a message "An unexpected error occurred: " followed by the error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") long userId) {
        if (!userRepository.existsById(userId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId);
        UserDTO userDTO = entityDtoMapper.convertToUserDTO(user);
        
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * Handles the HTTP PUT request to update a user.
     *
     * @param userId The ID of the user to update.
     *               If the user with the provided ID doesn't exist, return a NOT_FOUND status with a message "User with the provided id doesn't exist".
     *               If the user with the provided ID exists, proceed with updating the user.
     *               If the user is updated successfully, return an OK status with a message "User updated successfully".
     *               If an unexpected error occurs, return a BAD_REQUEST status with a message "An unexpected error occurred: " followed by the error message.
     *               If the user with the provided ID doesn't exist, return a NOT_FOUND status with a message "User with the provided id doesn't exist".
     * @param updatedUser The updated user details.
     *
     * @return
     * A ResponseEntity indicating the result of the operation.
     * If the user is updated successfully, return an OK status with a message "User updated successfully".
     * If the user with the provided ID doesn't exist, return a NOT_FOUND status with a message "User with the provided id doesn't exist".
     * If an unexpected error occurs, return a BAD_REQUEST status with a message "An unexpected error occurred: " followed by the error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "id") long userId, @RequestBody User updatedUser){
        if(!userRepository.existsById(userId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with the provided id doesn't exist");
        }
        try {
            userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok("Shipment updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Handles the HTTP DELETE request to delete a user.
     * @param userId The ID of the user to delete.
     * @return
     * A ResponseEntity indicating the result of the operation.
     * If the user is deleted successfully, return an OK status with a message "User deleted successfully".
     * If the user with the provided ID doesn't exist, return a NOT_FOUND status with a message "User with the provided id doesn't exist".
     * If an unexpected error occurs, return a BAD_REQUEST status with a message "An unexpected error occurred: " followed by the error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the provided id doesn't exist");
        }
    }




}


