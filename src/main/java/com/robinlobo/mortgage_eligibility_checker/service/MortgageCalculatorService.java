package com.robinlobo.mortgage_eligibility_checker.service;

import org.springframework.stereotype.Service;

@Service
public class MortgageCalculatorService {

    private final MortgageRulesService mortgageRulesService;
    private final MortgageValidationService validationService;

    public MortgageCalculatorService(
                                     MortgageRulesService mortgageRulesService,
                                     MortgageValidationService validationService){

        this.mortgageRulesService = mortgageRulesService;
        this.validationService = validationService;
    }

    public Double calculateMonthlyMortgage(Long loanAmount, Double monthlyInterestRate, Integer maturityPeriodInMonths) {
        validationService.validateInputs(loanAmount, monthlyInterestRate, maturityPeriodInMonths);

        if (monthlyInterestRate == 0.0) {
            return loanAmount.doubleValue() / maturityPeriodInMonths;
        }

        double monthlyInitialPayment = Math.pow(1 + monthlyInterestRate, maturityPeriodInMonths);
        return loanAmount.doubleValue() *
                ((monthlyInterestRate * monthlyInitialPayment) / (monthlyInitialPayment - 1));
    }

    public boolean isMortgageTooHigh(double monthlyPayment, double monthlyIncome) {
        return mortgageRulesService.isMortgageTooHigh(monthlyPayment, monthlyIncome);
    }

    public boolean isMortgageOverHomeValue(double totalMortgageAmount, double homeValue) {
        return mortgageRulesService.isMortgageOverHomeValue(totalMortgageAmount, homeValue);
    }
}


