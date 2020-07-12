package com.exercise.product.pricing.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PriceRequest {

    String range;

    @NotNull(message = "Minimum Price is mandatory")
    Float minPrice;

    @NotNull(message = "Maximum Price is mandatory")
    Float maxPrice;

}
