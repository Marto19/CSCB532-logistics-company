package com.logistics.logisticsCompany.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a request for authentication, typically used in authentication processes.
 * This class encapsulates user credentials, including username and password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /**
     * The username associated with the authentication request.
     */
    private  String username;
    /**
     * The password associated with the authentication request.
     */
    private String password;
}