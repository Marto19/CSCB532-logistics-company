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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRoleServiceImpl userRoleService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;  // Add this line
    private final EntityDtoMapper entityDtoMapper;
    
    @Autowired
    public UserController(UserServiceImpl userServiceImpl, UserRoleServiceImpl userRoleService, UserRepository userRepository, UserRoleRepository userRoleRepository, EntityDtoMapper entityDtoMapper) {
        this.userService = userServiceImpl;
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;  // Add this line
        this.userRoleRepository = userRoleRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Validate user data and register the user
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        // Implement login logic using loginRequest.getUsername() and loginRequest.getPassword()
        // Return a token or session information upon successful login
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }


//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "index"; // Assuming you have a login.html page
//    }




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

    //////////////////////CRUD OPERATIONS 3. b)///////////
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
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") long userId) {
        if (!userRepository.existsById(userId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId);
        UserDTO userDTO = entityDtoMapper.convertToUserDTO(user);
        
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the provided id doesn't exist");
        }
    }




}


