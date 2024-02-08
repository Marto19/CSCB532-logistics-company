package com.logistics.logisticsCompany.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a filter that authenticates JWT tokens. It extends {@link OncePerRequestFilter} to ensure a single execution per request dispatch.
 * It uses Spring's @Component and @Service annotations to indicate that it is a bean and it should be automatically detected by spring-boot's @ComponentScan.
 * It also uses Lombok's @RequiredArgsConstructor to generate a constructor that initializes final fields.
 */
@Component
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * The JwtService instance used for JWT-related operations.
     */
    private final JwtService jwtService;
    /**
     * The UserDetailsService instance used for user-related operations.
     */
    private final UserDetailsService userDetailsService;
    /**
     * This method attempts to authenticate the JWT token in the request. If the token is valid, it sets the authentication in the context.
     * It is called once per request.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param filterChain the FilterChain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        
        final String requestURI = request.getRequestURI();
        
        // List of paths to allow without authentication
        List<String> allowedPaths = Arrays.asList("/api/v1/auth/register", "/api/v1/auth/authenticate", "/login", "/register");
        
        // Check if the request URI is in the list of allowed paths
        if (allowedPaths.stream().anyMatch(path -> requestURI.startsWith(path))) {
            filterChain.doFilter(request, response); // Skip JWT check and continue filter chain
            return;
        }
        
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No JWT token found in request headers");
            return; // Stop processing the request here
        }
        
        final String jwt = authHeader.substring(7); // Extract the JWT token
        try {
            final String userName = jwtService.extractUsername(jwt);
            
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // In case of an error (token is invalid, user does not exist, etc.)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token: " + e.getMessage());
            return; // Stop processing the request here
        }
        
        filterChain.doFilter(request, response);
    }
}