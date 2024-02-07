package com.logistics.logisticsCompany.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a response to an authentication request, typically containing an authentication token.
 * This class encapsulates the token generated during the authentication process.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    /**
     * The authentication token associated with the authentication response.
     */
    private String token;
}