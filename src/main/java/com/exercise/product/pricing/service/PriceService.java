package com.exercise.product.pricing.service;

import com.exercise.product.pricing.domain.PriceRequest;
import com.exercise.product.pricing.domain.PriceResponse;
import com.exercise.product.pricing.entity.Price;
import com.exercise.product.pricing.exception.domain.NotFoundException;
import com.exercise.product.pricing.exception.domain.PriceInformationAlreadyExists;
import com.exercise.product.pricing.repository.PriceRepository;
import com.exercise.product.pricing.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ProductRepository productRepository;

    private PriceResponse buildPriceResponse(Price price) {
        PriceResponse priceResponse = PriceResponse.builder().build();
        BeanUtils.copyProperties(price, priceResponse);
        return priceResponse;
    }

    public Mono<PriceResponse> addPrice(String productId, PriceRequest priceRequest) {

        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Product "+ productId + "not found")))
                .flatMap(product -> {
                    Mono<Boolean> productPresent = priceRepository.findById(productId).hasElement();
                    return productPresent.flatMap(isPresent -> {
                        if(isPresent){
                            return Mono.error(new PriceInformationAlreadyExists
                                    ("Price Information Already Exists for product " + productId));
                        }
                        Price price = Price.builder().productId(productId).build();
                        BeanUtils.copyProperties(priceRequest, price);
                        return priceRepository.save(price).map(p2 -> buildPriceResponse(p2));
                    });
                });
    }

    public Mono<PriceResponse> getPrice(String productId)  {
        return priceRepository.findById(productId)
                .switchIfEmpty( Mono.error(() -> new NotFoundException("Price Information not found for product" + productId)))
                .map(priceInfo -> buildPriceResponse(priceInfo));
    }

}
