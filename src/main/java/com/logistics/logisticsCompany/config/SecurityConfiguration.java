package com.logistics.logisticsCompany.config;

import com.logistics.logisticsCompany.entities.users.User;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ //1:55:40
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/api/v1/auth/**", "/login", "/register","/admin/adminpage"
                        , "/api/v1/logistics-companies", "/api/v1/logistics-companies")   //todo: change whitelist pages
                        .permitAll()
                        //TODO: FETCH THE ROLES PROPERLY, MAYBE WHEN LOGIN?
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/assign-roles").hasRole("ADMIN")
                                .requestMatchers("/api/v1/logistics-companies/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/employees/**").hasRole("EMPLOYEE")
                                .requestMatchers("/api/v1/users/**").hasRole("USER")
                                .requestMatchers("/api/v1/customers/**").hasRole("CUSTOMER")
                                .requestMatchers("/api/v1/shipments/**").hasRole("EMPLOYEE")
                        .anyRequest()
                        .authenticated()
                        //.and() of course .and() is deprecated, because why not
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //spring will create new session for each request
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

//        1. Register/Login Requests:
//
//        Role: USER
//        Endpoints:
//        POST /api/v1/users/login
//        POST /api/v1/users/register
//        POST /api/v1/auth/authenticate
//
//        2. Set User Roles Requests:
//
//        Role: ADMIN
//        Endpoints:
//        POST /api/v1/users/assign-roles
//
//        3. Logistics Company CRUD Requests:
//        a) Logistics Company:
//
//        Role: ADMIN
//        Endpoints:
//        POST /api/v1/logistics-companies
//        GET /api/v1/logistics-companies
//        GET /api/v1/logistics-companies/{id}
//        PUT /api/v1/logistics-companies/{id}
//        DELETE /api/v1/logistics-companies/{id}
//
//        b) Employee CRUD Requests:
//
//        Role: EMPLOYEE
//        Endpoints:
//        POST /api/v1/employees
//        GET /api/v1/employees
//        GET /api/v1/employees/{id}
//        PUT /api/v1/employees/{id}
//        DELETE /api/v1/employees/{id}
//
//        c) User CRUD Requests:
//
//        Role: USER
//        Endpoints:
//        POST /api/v1/users
//        GET /api/v1/users
//        GET /api/v1/users/{id}
//        PUT /api/v1/users/{id}
//        DELETE /api/v1/users/{id}
//
//        d) Customer CRUD Requests:
//
//        Role: CUSTOMER
//        Endpoints:
//        POST /api/v1/customers
//        GET /api/v1/customers
//        GET /api/v1/customers/{id}
//        PUT /api/v1/customers/{id}
//        DELETE /api/v1/customers/{id}
//
//        4. Register Sent/Received Goods CRUD Requests:
//
//        Role: EMPLOYEE
//        Endpoints:
//        POST /api/v1/shipments/sent
//        POST /api/v1/shipments/received
//        GET /api/v1/shipments
//        GET /api/v1/shipments/{id}
//        GET /api/v1/shipments/employee/{employeeId}
//        PUT /api/v1/shipments/{id}
//        DELETE /api/v1/shipments/{id}
//        Role: USER
//
//        Endpoints:
//        GET /api/v1/users/{userId} (To fetch details of their own profile)
//        GET /api/v1/shipments/employee/{employeeId}(To fetch their own order and stuff idk)
