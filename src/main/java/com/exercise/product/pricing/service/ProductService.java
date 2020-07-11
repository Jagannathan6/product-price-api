package com.exercise.product.pricing.service;

import com.exercise.product.pricing.domain.ProductRequest;
import com.exercise.product.pricing.domain.ProductResponse;
import com.exercise.product.pricing.domain.Workflow;
import com.exercise.product.pricing.entity.Product;
import com.exercise.product.pricing.exception.domain.NotFoundException;
import com.exercise.product.pricing.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private ProductResponse buildProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder().build();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }


    public Mono<ProductResponse> getProduct(String productId)  {
        return productRepository.findById(productId)
               .switchIfEmpty( Mono.error(() -> new NotFoundException("Product not found")))
               .map(product -> buildProductResponse(product));
    }

    public Mono<ProductResponse> addProduct(ProductRequest productRequest) {
        log.info("Adding {} to products", productRequest.getTitle());
        LocalDateTime currentTime = LocalDateTime.now();
        Workflow workflow = Workflow.builder()
                .status("NEW").build();
        Product product = Product.builder()
                .workflow(workflow)
                .publishedAt(currentTime)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        BeanUtils.copyProperties(productRequest, product);
        return productRepository.save(product).map( product1 -> buildProductResponse(product1));
    }

    public Mono<ProductResponse> editProduct(String productId, ProductRequest productRequest)  {
        return productRepository.findById(productId)
                .switchIfEmpty( Mono.defer(() -> Mono.error(() -> new NotFoundException("Product not found"))))
                .flatMap(p1 -> {
                    BeanUtils.copyProperties(productRequest, p1);
                    p1.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(p1).map(product1 -> buildProductResponse(product1));
                });
    }
}
