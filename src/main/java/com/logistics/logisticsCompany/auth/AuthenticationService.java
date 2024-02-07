package com.logistics.logisticsCompany.auth;

import com.logistics.logisticsCompany.config.JwtService;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
/**
 * The {@code AuthenticationService} class provides authentication-related services within the logistics company application.
 * It is responsible for user registration and authentication, utilizing the provided {@link UserRepository},
 * {@link PasswordEncoder}, {@link JwtService}, and {@link AuthenticationManager} dependencies.
 *
 * <p>This service class interacts with the database through the {@link UserRepository} and {@link UserRoleRepository} to manage
 * user-related data and roles. It also utilizes the {@link PasswordEncoder} to securely encode and store passwords,
 * {@link JwtService} for generating JWT tokens, and {@link AuthenticationManager} for authenticating users during login.</p>
 *
 * <p>Note: The service assumes a specific role (e.g., role with ID 1) during user registration. Ensure that the
 * {@link UserRoleRepository} is properly configured to provide the desired role.</p>
 *
 * @version 1.0
 * @since 2024-02-05
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {


    /**
     * The {@link UserRepository} instance used for user-related operations.
     */
    private final UserRepository userRepository;

    /**
     * The {@link PasswordEncoder} instance used for password encoding and validation.
     */
    private final PasswordEncoder passwordEncoder;  //todo: add a bean -fixed - the bean wasn't public in ApplicationConfig

    /**
     * The {@link JwtService} instance used for JWT-related operations.
     */
    private  final JwtService jwtService;

    /**
     * The {@link AuthenticationManager} instance used for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The {@link UserRoleRepository} instance used for user role-related operations.
     */
    private final UserRoleRepository userRoleRepository;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param request The {@link RegisterRequest} object containing user registration details.
     * @return An {@link AuthenticationResponse} containing the generated JWT token after successful registration.
     *
     * @see RegisterRequest
     * @see AuthenticationResponse
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username already exists.");
        }
        
        // method for role
        Set<UserRole> defaultRoles = getDefaultRolesForNewUser();
        
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                //marto - Add the desired role to the set// todo: find a way to put
                //caki - found way to put.
                .userRoleList(defaultRoles)//user role put.
                .build();
        userRepository.save(user);
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)  //1:52:21
                .build();
    }
    
    private Set<UserRole> getDefaultRolesForNewUser() {
        // Example: Fetching default role by name or id, here assuming a role name 'USER'
        UserRole defaultRole = userRoleRepository.findByUserRole("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalArgumentException("Default user role not found."));
        return Set.of(defaultRole);
    }
    /**
     * Authenticates a user with the provided credentials during login.
     *
     * @param request The {@link AuthenticationRequest} object containing user authentication details.
     * @return An {@link AuthenticationResponse} containing the generated JWT token after successful authentication.
     *
     * @throws "AuthenticationException" If authentication fails.
     *
     * @see AuthenticationRequest
     * @see AuthenticationResponse
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {     //login authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user.getId();
        }
        // Consider your strategy for unauthenticated requests
        throw new IllegalStateException("User is not authenticated");
    }
}