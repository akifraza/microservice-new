package com.akifcode.signupservice.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akifcode.signupservice.model.User;
import com.akifcode.signupservice.repositories.UserRepository;
import com.akifcode.signupservice.services.UserService;
import com.akifcode.signupservice.utils.ResponseHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SignupController {
    
    private UserService userService;

    private UserRepository userRepository;

    @PostMapping ("/api/signup")
    public ResponseEntity<?> createUser (@RequestBody User user) {
        User user1 = new User();
        user1 = userRepository.findByUsername(user.getUsername());
        if (user1 == null)
        {
            userService.createuser(user);
            new ResponseHandler();
            return ResponseHandler.generateResponse("true", HttpStatus.CREATED, user);
        } else {
            return new ResponseEntity<>("User Already Exist.", HttpStatus.CONFLICT);
        }
    }

}
