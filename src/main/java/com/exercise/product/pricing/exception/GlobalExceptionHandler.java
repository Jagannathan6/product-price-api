package com.exercise.product.pricing.exception;


import com.exercise.product.pricing.exception.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public NotFound productNotFound(NotFoundException ex) {
        log.error("Not Found Error {}", ex.getMessage());
        NotFound notFound = NotFound.builder()
                .errorMsg(ex.getMessage())
                .build();
        return notFound;
    }


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ApiRequestError> handleManadatoryParametersError(WebExchangeBindException e) {
        List<FieldError> fieldErrors =  e.getFieldErrors();
        List<ApiRequestError> apiRequestErrors = new ArrayList<>();
        for (FieldError fe : fieldErrors) {
            ApiRequestError apiRequestError = ApiRequestError
                    .builder()
                    .fieldName(fe.getField())
                    .rejectedReason(fe.getDefaultMessage())
                    .build();
            apiRequestErrors.add(apiRequestError);
        }
        return apiRequestErrors;
    }

    @ExceptionHandler(PriceInformationAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handlePriceAlreadyExists(PriceInformationAlreadyExists e) {
        log.error(" Price Information already exists" + e.getMessage());
        return ApiError.builder().errorMessage(e.getMessage()).build();
    }


}
