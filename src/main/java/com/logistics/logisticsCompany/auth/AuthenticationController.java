package com.logistics.logisticsCompany.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * The {@code AuthenticationController} class is a Spring MVC RESTful controller responsible for handling authentication-related
 * requests within the logistics company application. It exposes endpoints for user registration and authentication.
 *
 * <p>This controller is designed to work in conjunction with the {@link AuthenticationService} to perform registration
 * and authentication operations.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-02-05
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    /**
     * Handles the HTTP POST request for user registration.
     *
     * <p>This endpoint allows clients to register new users by providing necessary registration details
     * through the request body in JSON format.</p>
     *
     * @param request The {@link RegisterRequest} object containing user registration details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the result of the registration.
     *
     * @see RegisterRequest
     * @see AuthenticationResponse
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> responseResponseEntity(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Handles the HTTP POST request for user authentication.
     *
     * <p>This endpoint allows clients to authenticate users by providing their credentials through the request body
     * in JSON format.</p>
     *
     * @param request The {@link AuthenticationRequest} object containing user authentication details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the result of the authentication.
     *
     * @see AuthenticationRequest
     * @see AuthenticationResponse
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticationResponseResponseEntity(@RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
}