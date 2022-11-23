package com.akifcode.signinservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akifcode.signinservice.model.AuthenticationRequest;
import com.akifcode.signinservice.model.AuthenticationResponse;
import com.akifcode.signinservice.model.CustomUser;
import com.akifcode.signinservice.repositories.UserRepository;
import com.akifcode.signinservice.utils.Jwtutil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SigninController {
    

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private Jwtutil jwtutil;

    @PostMapping ("/api/authenticate")
    public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } catch (BadCredentialsException e) {
                throw new Exception("Incorrect Username or Password", e);
            }
            final UserDetails userDetails = new CustomUser(userRepository.findByUsername(authenticationRequest.getUsername()));
            final String jwt = jwtutil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}