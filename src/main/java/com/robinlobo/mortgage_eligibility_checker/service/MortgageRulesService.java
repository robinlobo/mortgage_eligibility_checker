package com.robinlobo.mortgage_eligibility_checker.service;

import org.springframework.stereotype.Component;

@Component
public class MortgageRulesService {

    // Rule: Monthly mortgage must not exceed 4× monthly income
    public boolean isMortgageTooHigh(double monthlyPayment, double monthlyIncome) {
        return monthlyPayment > monthlyIncome * 4;
    }

    // Rule: Total mortgage must not exceed home value
    public boolean isMortgageOverHomeValue(double totalMortgageAmount, double homeValue) {
        return totalMortgageAmount >= homeValue;
    }
}

