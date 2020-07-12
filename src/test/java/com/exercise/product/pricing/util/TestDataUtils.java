package com.exercise.product.pricing.util;

import com.exercise.product.pricing.domain.*;
import com.exercise.product.pricing.entity.Price;
import com.exercise.product.pricing.entity.Product;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TestDataUtils {

    static String productId = "abcdefgh";

    public static PriceRequest getPriceRequest() {
        return PriceRequest.builder()
               .range("1-2")
               .maxPrice(5.20f)
               .minPrice(4.00f)
               .build();
    }

    public static PriceRequest getPriceRequestWithError() {
        return PriceRequest.builder()
                .range("1-2")
                .build();
    }

    public static PriceResponse getPriceResponse() {
        return PriceResponse.builder()
                .productId(productId)
                .range("1-2")
                .maxPrice(5.20f)
                .minPrice(4.00f)
                .build();
    }

    public static ProductRequest getProductRequest() {
        MetaFields fields = MetaFields.builder().key("test").build();
        ProductRequest productRequest = ProductRequest
                .builder()
                .backOrder(true)
                .sellerId("124")
                .title("Choclate")
                .manufacturer("Nestle")
                .soldOut(true)
                .visible(false)
                .requiresShopping(false)
                .metaFields(Arrays.asList(fields))
                .build();

        return productRequest;
    }

    public static ProductRequest getProductInvalidRequest() {
        MetaFields fields = MetaFields.builder().key("test").build();
        ProductRequest productRequest = ProductRequest
                .builder()
                .backOrder(true)
                .sellerId("124")
                .soldOut(true)
                .visible(false)
                .requiresShopping(false)
                .metaFields(Arrays.asList(fields))
                .build();

        return productRequest;
    }

    public static ProductResponse getProductResponse() {
        Workflow workflow = Workflow.builder().status("NEW").build();
        MetaFields fields = MetaFields.builder().key("test").build();
        ProductResponse productResponse = ProductResponse
                .builder()
                .id(productId)
                .backOrder(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(LocalDateTime.now())
                .sellerId("124")
                .title("Choclate")
                .manufacturer("Nestle")
                .soldOut(true)
                .visible(false)
                .requiresShopping(false)
                .workflow(workflow)
                .metaFields(Arrays.asList(fields))
                .build();

        return productResponse;
    }


    public static Product getProductEntityWithNoId() {
        Workflow workflow = Workflow.builder().status("NEW").build();
        MetaFields fields = MetaFields.builder().key("test").build();
        Product product = Product
                .builder()
                .backOrder(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(LocalDateTime.now())
                .sellerId("124")
                .title("Choclate")
                .manufacturer("Nestle")
                .soldOut(true)
                .visible(false)
                .requiresShopping(false)
                .workflow(workflow)
                .metaFields(Arrays.asList(fields))
                .build();

        return product;
    }

    public static Price getPriceEnity() {
        Price price = Price.builder()
                .productId(productId)
                .maxPrice(2.00f)
                .minPrice(1.00f)
                .build();
        return price;

    }

    public static Product getProductEntity() {
        Workflow workflow = Workflow.builder().status("NEW").build();
        MetaFields fields = MetaFields.builder().key("test").build();
        Product product = Product
                .builder()
                .id(productId)
                .backOrder(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(LocalDateTime.now())
                .sellerId("124")
                .title("Choclate")
                .manufacturer("Nestle")
                .soldOut(true)
                .visible(false)
                .requiresShopping(false)
                .workflow(workflow)
                .metaFields(Arrays.asList(fields))
                .build();

        return product;
    }


}
