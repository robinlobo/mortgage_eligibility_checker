package com.robinlobo.mortgage_eligibility_checker.exception;

import com.robinlobo.mortgage_eligibility_checker.util.MortgageApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class MortgageExceptionHandler {

    // https://dev.to/abdelrani/error-handling-in-spring-web-using-rfc-9457-specification-5dj1
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MortgageApiResponse<ProblemDetail> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        ProblemDetail problemDetail = handleValidationException(ex);
        return MortgageApiResponse.error(problemDetail);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public MortgageApiResponse<ProblemDetail> handleIllegalArgumentExceptions(
            IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problemDetail =  ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        return MortgageApiResponse.error(problemDetail);
    }

    public Map<String, String> handleValidationExceptionsv2(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    // https://blog.frankel.ch/problem-details-http-apis/
    private ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        String details = getErrorsDetails(ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), details);
        problemDetail.setType(URI.create("http://localhost:8080/errors/bad-request"));
        problemDetail.setTitle("Bad request");
        problemDetail.setInstance(ex.getBody().getInstance());
        problemDetail.setProperty("timestamp", Instant.now()); // adding more data using the Map properties of the ProblemDetail
        return problemDetail;
    }

    private String getErrorsDetails(MethodArgumentNotValidException ex) {
        return Optional.of(ex.getDetailMessageArguments())
                .map(args -> Arrays.stream(args)
                        .filter(msg -> !ObjectUtils.isEmpty(msg))
                        .reduce("Please make sure to provide a valid request, ", (a, b) -> a + " " + b)
                )
                .orElse("").toString();
    }


}
