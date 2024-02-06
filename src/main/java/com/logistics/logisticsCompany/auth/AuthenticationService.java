package com.logistics.logisticsCompany.auth;

import com.logistics.logisticsCompany.config.JwtService;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;  //todo: add a bean -fixed - the bean wasn't public in ApplicationConfig
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
    public AuthenticationResponse register(RegisterRequest request) {
        
        //fixme added from caki - check if its ok
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new EntityNotFoundException("Username already taken"); // Use a more specific exception if you like
        }
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoleList(Set.of(userRoleRepository.findUserRoleById(1))) // Add the desired role to the set// todo: find a way to put
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)        //1:52:21
                .build();

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
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}