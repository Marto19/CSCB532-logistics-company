package com.logistics.logisticsCompany.config;

import com.logistics.logisticsCompany.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * This class is a configuration class for the application.
 * It uses Spring's @Configuration annotation to indicate that it is a configuration class.
 * It also uses Lombok's @RequiredArgsConstructor to generate a constructor that initializes final fields.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    /**
     * The UserRepository instance used for user-related operations.
     */
    private final UserRepository repository;
    /**
     * This bean provides a UserDetailsService implementation that fetches user details from the UserRepository.
     * @return a UserDetailsService implementation
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    /**
     * This bean provides an AuthenticationProvider implementation that uses the UserDetailsService and a password encoder.
     * @return an AuthenticationProvider implementation
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){ //the data access object DAO, responsible for fetching the users data
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    /**
     * This bean provides a PasswordEncoder implementation that uses BCrypt.
     * @return a PasswordEncoder implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * This bean provides an AuthenticationManager implementation.
     * @param config the AuthenticationConfiguration instance
     * @return an AuthenticationManager implementation
     * @throws Exception if an error occurs when getting the AuthenticationManager
     */
    @Bean       //creating the auth manager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}