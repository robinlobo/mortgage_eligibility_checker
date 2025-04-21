package com.robinlobo.mortgage_eligibility_checker.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MortgageApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> MortgageApiResponse<T> empty() {
        return success(null, "Success");
    }

    public static <T> MortgageApiResponse<T> success(T data, String message) {
        return MortgageApiResponse.<T>builder()
                .message(message)
                .data(data)
                .success(true)
                .build();
    }

    public static <T> MortgageApiResponse<T> error(T data) {
        return MortgageApiResponse.<T>builder()
                .data(data)
                .success(false)
                .build();
    }



}
