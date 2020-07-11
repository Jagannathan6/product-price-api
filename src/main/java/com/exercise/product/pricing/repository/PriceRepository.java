package com.exercise.product.pricing.repository;

import com.exercise.product.pricing.entity.Price;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends ReactiveMongoRepository<Price, String> {


}