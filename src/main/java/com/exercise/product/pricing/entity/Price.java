package com.exercise.product.pricing.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "prices")
@ToString
public class Price {

    @Id
    String productId;

    String range;

    float minPrice;

    float maxPrice;

}
