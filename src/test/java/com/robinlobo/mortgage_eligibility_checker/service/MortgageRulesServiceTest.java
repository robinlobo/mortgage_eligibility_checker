package com.robinlobo.mortgage_eligibility_checker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MortgageRulesServiceTest {

    private MortgageRulesService rulesService;

    @BeforeEach
    void setUp() {
        rulesService = new MortgageRulesService();
    }

    @ParameterizedTest(name = "MonthlyPayment: {0}, MonthlyIncome: {1}")
    @CsvSource({
            "5000, 1000",   // Exceeds 4x income
            "4200, 1000"    // Exceeds 4x income
    })
    @DisplayName("Should return true when mortgage payment exceeds 4x income")
    void testIsMortgageTooHighTrue(double monthlyPayment, double monthlyIncome) {
        assertTrue(rulesService.isMortgageTooHigh(monthlyPayment, monthlyIncome));
    }

    @ParameterizedTest(name = "MonthlyPayment: {0}, MonthlyIncome: {1}")
    @CsvSource({
            "3999.99, 1000",
            "2000, 1000"
    })
    @DisplayName("Should return false when mortgage payment is within 4x income")
    void testIsMortgageTooHighFalse(double monthlyPayment, double monthlyIncome) {
        assertFalse(rulesService.isMortgageTooHigh(monthlyPayment, monthlyIncome));
    }

    @ParameterizedTest(name = "TotalMortgage: {0}, HomeValue: {1}")
    @CsvSource({
            "500000, 400000",
            "300000, 300000"
    })
    @DisplayName("Should return true when total mortgage exceeds or equals home value")
    void testIsMortgageOverHomeValueTrue(double totalMortgage, double homeValue) {
        assertTrue(rulesService.isMortgageOverHomeValue(totalMortgage, homeValue));
    }

    @ParameterizedTest(name = "TotalMortgage: {0}, HomeValue: {1}")
    @CsvSource({
            "299999.99, 300000",
            "250000, 300000"
    })
    @DisplayName("Should return false when total mortgage is below home value")
    void testIsMortgageOverHomeValueFalse(double totalMortgage, double homeValue) {
        assertFalse(rulesService.isMortgageOverHomeValue(totalMortgage, homeValue));
    }
}
