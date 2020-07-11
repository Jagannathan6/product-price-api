package com.exercise.product.pricing.service;

import com.exercise.product.pricing.domain.ProductResponse;
import com.exercise.product.pricing.entity.Product;
import com.exercise.product.pricing.exception.domain.NotFoundException;
import com.exercise.product.pricing.repository.ProductRepository;
import com.exercise.product.pricing.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    String productId = "abcdefgh";

    @Test
    public void getProduct(){
        Product product = TestDataUtils.getProductEntity();
        when(productRepository.findById(productId)).thenReturn(Mono.just(product));
        Mono<ProductResponse> productResponseMono = productService.getProduct(productId);
        ProductResponse response = productResponseMono.block();
        assertNotNull(response);
    }

    @Test
    public void getProductWithError(){
        when(productRepository.findById(productId)).thenReturn(Mono.empty());
        StepVerifier
                .create(productService.getProduct(productId))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().equalsIgnoreCase("Product not found"))
                .verify();
    }

    @Test
    public void testAddProduct(){
        when(productRepository.save(any()))
                .thenReturn(Mono.just(TestDataUtils.getProductEntity()));
        Mono<ProductResponse> productResponse = productService.addProduct(TestDataUtils.getProductRequest());
        Mockito.verify(productRepository, times(1)).save(any());
        ProductResponse response = productResponse.block();
        assertNotNull(response.getId());
    }

    @Test
    public void testEditProductWithError(){
        when(productRepository.findById(productId)).thenReturn(Mono.empty());
        StepVerifier
                .create(productService.editProduct(productId, TestDataUtils.getProductRequest()))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().equalsIgnoreCase("Product not found"))
                .verify();
    }

    @Test
    public void testEditProduct(){
        when(productRepository.findById(productId)).thenReturn(Mono.just(TestDataUtils.getProductEntity()));
        when(productRepository.save(any())).thenReturn(Mono.just(TestDataUtils.getProductEntity()));
        Mono<ProductResponse> responseMono = productService.editProduct(productId, TestDataUtils.getProductRequest());
        ProductResponse response = responseMono.block();
        Mockito.verify(productRepository, times(1)).save(any());
        assertNotNull(response.getId());

    }
}
