package com.exercise.product.pricing.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductRequest {

    @NotBlank(message =  "Seller ID cannot be blank")
    private String sellerId;

    @NotBlank(message =  "Title cannot be blank")
    private String title;

    @NotBlank(message =  "Manufacturer cannot be blank")
    private String manufacturer;

    private boolean lowQuantity;

    private boolean soldOut;

    private boolean backOrder;

    private boolean visible;

    private boolean requiresShopping;

    private List<MetaFields> metaFields;

}
