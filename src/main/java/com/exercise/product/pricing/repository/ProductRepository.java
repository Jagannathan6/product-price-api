package com.exercise.product.pricing.repository;

import com.exercise.product.pricing.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {


}
