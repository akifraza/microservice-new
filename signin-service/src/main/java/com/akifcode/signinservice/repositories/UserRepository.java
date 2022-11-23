package com.akifcode.signinservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.akifcode.signinservice.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
