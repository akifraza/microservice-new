package com.akifcodes.productservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.akifcodes.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
