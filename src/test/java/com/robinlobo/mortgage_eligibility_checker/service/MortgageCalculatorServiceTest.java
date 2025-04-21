package com.robinlobo.mortgage_eligibility_checker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MortgageCalculatorServiceTest {

    private MortgageRulesService rulesService;
    private MortgageValidationService validationService;
    private MortgageCalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        rulesService = mock(MortgageRulesService.class);
        validationService = mock(MortgageValidationService.class);
        calculatorService = new MortgageCalculatorService(rulesService, validationService);
    }

    @Test
    void shouldCalculateMonthlyMortgageWithZeroInterest() {
        Long loanAmount = 120000L;
        double result = calculatorService.calculateMonthlyMortgage(loanAmount, 0.0, 12);
        assertEquals(10000.0, result);
        verify(validationService).validateInputs(loanAmount, 0.0, 12);
    }

    @Test
    void shouldCalculateMonthlyMortgageWithInterest() {
        Long loanAmount = 200000L;
        Double monthlyInterestRate = 0.004;
        Integer months = 360;
        double result = calculatorService.calculateMonthlyMortgage(loanAmount, monthlyInterestRate, months);

        double expected = loanAmount * ((monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months)) /
                (Math.pow(1 + monthlyInterestRate, months) - 1));
        assertEquals(expected, result, 0.01);
        verify(validationService).validateInputs(loanAmount, monthlyInterestRate, months);
    }

    @ParameterizedTest
    @CsvSource({
            "5000, 1000, true",
            "2000, 1000, false"
    })
    void shouldEvaluateMortgageTooHigh(double payment, double income, boolean expected) {
        when(rulesService.isMortgageTooHigh(payment, income)).thenReturn(expected);
        assertEquals(expected, calculatorService.isMortgageTooHigh(payment, income));
    }

    @ParameterizedTest
    @CsvSource({
            "400000, 350000, true",
            "250000, 350000, false"
    })
    void shouldEvaluateMortgageOverHomeValue(double mortgage, double homeValue, boolean expected) {
        when(rulesService.isMortgageOverHomeValue(mortgage, homeValue)).thenReturn(expected);
        assertEquals(expected, calculatorService.isMortgageOverHomeValue(mortgage, homeValue));
    }
}


