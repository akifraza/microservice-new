package com.akifcode.signupservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.akifcode.signupservice.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
