package com.logistics.logisticsCompany.auth;

import com.logistics.logisticsCompany.config.JwtService;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AutheticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;  //todo: add a bean
    private  final JwtService jwtService;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoleList(Set.of(UserRole.USER)) // Add the desired role to the set// todo: fix
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)        //1:52:21
                .build();
        
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
