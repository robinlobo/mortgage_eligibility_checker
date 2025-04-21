package com.robinlobo.mortgage_eligibility_checker.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MortgageConsultation(@NotNull @Positive Long income,
                                   @NotNull @Positive Integer maturityPeriod,
                                   @NotNull @Positive Long loanAmount,
                                   @NotNull @Positive Long homeValue) {
    
}
