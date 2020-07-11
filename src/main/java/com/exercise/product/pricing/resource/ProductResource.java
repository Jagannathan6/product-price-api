package com.exercise.product.pricing.resource;

import com.exercise.product.pricing.domain.ProductRequest;
import com.exercise.product.pricing.domain.ProductResponse;
import com.exercise.product.pricing.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/products")
@Validated
@Slf4j
public class ProductResource {

    @Autowired
    ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProducts(@RequestBody @Valid ProductRequest productRequest) {
        log.info(" Request to add {} product", productRequest);
        return productService.addProduct(productRequest);
    }

    @GetMapping(value = "/{product_id}")
    public Mono<ProductResponse> getProducts(@PathVariable("product_id") String productId) {
        log.info("Get the Product details of the Product ID {}", productId);
        return productService.getProduct(productId);
    }

    @PutMapping(value = "/{product_id}")
    public Mono<ProductResponse> editProducts(@PathVariable("product_id") String productId,
                                             @RequestBody @Valid ProductRequest productRequest) {
        log.info("Editing the Product details of the Product ID {}", productId);
        return productService.editProduct(productId, productRequest);
    }
}
