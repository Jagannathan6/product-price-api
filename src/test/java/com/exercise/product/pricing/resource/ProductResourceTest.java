package com.exercise.product.pricing.resource;

import com.exercise.product.pricing.domain.ProductRequest;
import com.exercise.product.pricing.domain.ProductResponse;
import com.exercise.product.pricing.exception.domain.ApiRequestError;
import com.exercise.product.pricing.service.ProductService;
import com.exercise.product.pricing.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductResource.class)
public class ProductResourceTest {

    @MockBean
    ProductService productService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testAddProduct() {
      ProductRequest productRequest = TestDataUtils.getProductRequest();
        when(productService.addProduct(productRequest)).thenReturn(Mono.just(TestDataUtils.getProductResponse()));
        FluxExchangeResult<ProductResponse>  result = webClient.post()
                .uri("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productRequest), ProductRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(ProductResponse.class);
        ProductResponse productResponse = result.getResponseBody().blockLast();
        assertTrue(StringUtils.isNotBlank(productResponse.getId()));
        Mockito.verify(productService, times(1)).addProduct(any());
    }

    @Test
    public void testAddProductWithInvalidRequest() {
        ProductRequest productRequest = TestDataUtils.getProductInvalidRequest();
        FluxExchangeResult<ApiRequestError> result = webClient.post()
                .uri("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productRequest), ProductRequest.class)
                .exchange()
                .expectStatus().is4xxClientError().returnResult(ApiRequestError.class);
    }

    @Test
    public void testGetProducts() {
        when(productService.getProduct("abcdefgh")).thenReturn(Mono.just(TestDataUtils.getProductResponse()));
        FluxExchangeResult<ProductResponse> productResponse = webClient.get()
                .uri("/v1/products/abcdefgh")
                .exchange()
                .expectStatus().is2xxSuccessful().returnResult(ProductResponse.class);
        ProductResponse response = productResponse.getResponseBody().blockLast();
        Mockito.verify(productService, times(1)).getProduct(any());
        assertTrue(StringUtils.isNotBlank(response.getId()));
    }

    @Test
    public void testEditProduct() {
        ProductRequest productRequest = TestDataUtils.getProductRequest();
        when(productService.editProduct("abcdefgh", productRequest)).thenReturn(Mono.just(TestDataUtils.getProductResponse()));
        FluxExchangeResult<ProductResponse>  result = webClient.put()
                .uri("/v1/products/abcdefgh")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productRequest), ProductRequest.class)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(ProductResponse.class);
        ProductResponse productResponse = result.getResponseBody().blockLast();
        assertTrue(StringUtils.isNotBlank(productResponse.getId()));
        Mockito.verify(productService, times(1)).editProduct(anyString(), any());
    }


}
