package com.exercise.product.pricing.entity;

import com.exercise.product.pricing.ProductConstants;
import com.exercise.product.pricing.domain.MetaFields;
import com.exercise.product.pricing.domain.Workflow;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "products")
@CompoundIndex(name = "productId", unique = true, def = "{'productId' : 1}")
@ToString
public class Product {

    @Id
    private String id;

    private UUID productId;

    private String sellerId;

    private String title;

    private String manufacturer;

    private boolean lowQuantity;

    private boolean soldOut;

    private boolean backOrder;

    private boolean visible;

    private boolean requiresShopping;

    private List<MetaFields> metaFields;

    private Workflow workflow;

    @JsonFormat(pattern = ProductConstants.DATE_FORMAT)
    private LocalDateTime publishedAt;

    @JsonFormat(pattern = ProductConstants.DATE_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = ProductConstants.DATE_FORMAT)
    private LocalDateTime updatedAt;

}
