package com.logistics.logisticsCompany.config;

import com.logistics.logisticsCompany.entities.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
//JWT validation service
//THIS IS CLASS IS MEANT TO MANIPULATE THE JWT TOKEN - Json Web Token
//generating, validating, extracting, ect
/**
 * This class is a service class for JWT token manipulation.
 * It uses Spring's @Service annotation to indicate that it is a service class.
 * It provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Service
public class JwtService {
    /**
     * The secret key used for signing the JWT.
     */
    private static final String SECRET_KEY = "9cb7d8506abbddfcc041c82d026a1964b087cc460266b2292894992dbf856cee";
    /**
     * The key used for signing the JWT, derived from the secret key.
     */
    private Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    /**
     * Extracts the username from the JWT token.
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Extracts a claim from the JWT token.
     * @param token the JWT token
     * @param claimsResolver the function to apply to the claims
     * @return the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * Generates a JWT token for the given user details.
     * @param userDetails the user details
     * @return the JWT token
     */
    public String generateToken(UserDetails userDetails){
        if (!(userDetails instanceof User)) {
            throw new IllegalArgumentException("UserDetails is not an instance of User");
        }
        User user = (User) userDetails;
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUserRoleList());
        claims.put("userId", user.getId());
        
        return generateToken(claims, userDetails.getUsername());
    }

    /**
     * Generates a JWT token for the given claims and subject.
     * @param claims the claims
     * @param subject the subject
     * @return the JWT token
     */
    private String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours token validity
                .signWith(key)
                .compact();
    }

    /**
     * Extracts all claims from the JWT token.
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is valid for the given user details.
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired.
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

}
