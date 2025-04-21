package com.robinlobo.mortgage_eligibility_checker.dto;

public record MortgageCalculationResult(boolean isFeasible, double monthlyPayment, double totalMortgageAmount, String statusMessage) {

}
