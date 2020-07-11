package com.exercise.product.pricing.resource;

import com.exercise.product.pricing.domain.PriceRequest;
import com.exercise.product.pricing.domain.PriceResponse;
import com.exercise.product.pricing.exception.domain.PriceInformationAlreadyExists;
import com.exercise.product.pricing.service.PriceService;
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
public class PricingResource {

    @Autowired
    PriceService priceService;

    @PostMapping(value = "/{product_id}/prices")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PriceResponse> addProducts(
            @PathVariable("product_id") String productId,
            @RequestBody @Valid PriceRequest priceRequest) {
        log.info(" Request to add price {} to product {}", priceRequest, productId);
        return priceService.addPrice(productId, priceRequest);
    }

    @GetMapping(value = "/{product_id}/prices")
    public Mono<PriceResponse> getPriceInformation(  @PathVariable("product_id") String productId) {
        log.info("Request to get a Price Information for Product {}", productId);
        return priceService.getPrice(productId);
    }
}
