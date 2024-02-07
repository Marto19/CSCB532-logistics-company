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
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){    //we check if the auth header is null or contains Bearer, all auth headers must have it
            filterChain.doFilter(request, response);      //pass the request and response to the next filter
            return;
        }
        jwt = authHeader.substring(7);  //we extract the jwt token
        userName = jwtService.extractUsername(jwt);
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) { //when the auth is null it means that the user is not yet authenticated
            //we check and get the user in the db
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            //check if the token is still valid or not
            if(jwtService.isTokenValid(jwt, userDetails)){
                //if true update the SecurityContextHolder and send the request to the dispatcher servlet
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}