package com.exercise.product.pricing.resource;

import com.exercise.product.pricing.domain.PriceRequest;
import com.exercise.product.pricing.domain.PriceResponse;
import com.exercise.product.pricing.service.PriceService;
import com.exercise.product.pricing.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PricingResource.class)
public class PriceResourceTest {

    @MockBean
    PriceService priceService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testGetPricing() {
        when(priceService.getPrice("abcdefgh")).thenReturn(Mono.just(TestDataUtils.getPriceResponse()));
        FluxExchangeResult<PriceResponse> productResponse = webClient.get()
                .uri("/v1/products/abcdefgh/prices")
                .exchange()
                .expectStatus().is2xxSuccessful().returnResult(PriceResponse.class);
        PriceResponse response = productResponse.getResponseBody().blockLast();
        Mockito.verify(priceService, times(1)).getPrice(any());
        assertTrue(StringUtils.isNotBlank(response.getProductId()));
    }

    @Test
    public void testAddPricing() {
        when(priceService.addPrice("abcdefgh", TestDataUtils.getPriceRequest()))
                .thenReturn(Mono.just(TestDataUtils.getPriceResponse()));
        FluxExchangeResult<PriceResponse> productResponse = webClient.post()
                .uri("/v1/products/abcdefgh/prices")
                .body(Mono.just(TestDataUtils.getPriceRequest()), PriceRequest.class)
                .exchange()
                .expectStatus().isCreated().returnResult(PriceResponse.class);
        PriceResponse response = productResponse.getResponseBody().blockLast();
        Mockito.verify(priceService, times(1)).addPrice(any(), any());
        assertTrue(StringUtils.isNotBlank(response.getProductId()));
    }

    @Test
    public void testAddPricingWithError() {
        when(priceService.addPrice("abcdefgh", TestDataUtils.getPriceRequestWithError()))
                .thenReturn(Mono.just(TestDataUtils.getPriceResponse()));
        FluxExchangeResult<String> productResponse = webClient.post()
                .uri("/v1/products/abcdefgh/prices")
                .body(Mono.just(TestDataUtils.getPriceRequestWithError()), PriceRequest.class)
                .exchange()
                .expectStatus().is4xxClientError().returnResult(String.class);
    }



}
