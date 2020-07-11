package com.exercise.product.pricing.service;

import com.exercise.product.pricing.domain.PriceResponse;
import com.exercise.product.pricing.exception.domain.NotFoundException;
import com.exercise.product.pricing.exception.domain.PriceInformationAlreadyExists;
import com.exercise.product.pricing.repository.PriceRepository;
import com.exercise.product.pricing.repository.ProductRepository;
import com.exercise.product.pricing.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PricingServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    PriceRepository priceRepository;

    @InjectMocks
    PriceService priceService;

    String productId = "abcdefgh";

    @Test
    public void testGetPrice() {
        when(priceRepository.findById(productId)).thenReturn(Mono.just(TestDataUtils.getPriceEnity()));
        Mono<PriceResponse> priceResponse = priceService.getPrice(productId);
        PriceResponse response = priceResponse.block();
        assertNotNull(response);
    }

    @Test
    public void testGetPriceNotFound() {
        when(priceRepository.findById(productId)).thenReturn(Mono.empty());
        StepVerifier
                .create(priceService.getPrice(productId))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().contains("not found"))
                .verify();
    }

    @Test
    public void testAddPriceWithProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Mono.empty());
        StepVerifier
                .create(priceService.addPrice(productId, TestDataUtils.getPriceRequest()))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().contains("not found"))
                .verify();
    }

    @Test
    public void testAddPriceWithPriceAlreadyExists() {
        when(productRepository.findById(productId)).thenReturn(Mono.just(TestDataUtils.getProductEntity()));
        when(priceRepository.findById(productId)).thenReturn(Mono.just(TestDataUtils.getPriceEnity()));
        StepVerifier
                .create(priceService.addPrice(productId, TestDataUtils.getPriceRequest()))
                .expectErrorMatches(throwable -> throwable instanceof PriceInformationAlreadyExists
                        && throwable.getMessage().contains("Price Information Already Exists for product"))
                .verify();
    }

    @Test
    public void testAddPrice() {
        when(productRepository.findById(productId)).thenReturn(Mono.just(TestDataUtils.getProductEntity()));
        when(priceRepository.findById(productId)).thenReturn(Mono.empty());
        when(priceRepository.save(any())).thenReturn(Mono.just(TestDataUtils.getPriceEnity()));
        Mono<PriceResponse> responseMono = priceService.addPrice(productId, TestDataUtils.getPriceRequest());
        PriceResponse response = responseMono.block();
        assertNotNull(response);
    }
}
