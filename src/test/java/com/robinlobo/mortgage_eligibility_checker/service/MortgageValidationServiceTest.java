package com.robinlobo.mortgage_eligibility_checker.service;

import com.robinlobo.mortgage_eligibility_checker.config.MortgageConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MortgageValidationServiceTest {

    private MortgageValidationService validationService;

    @BeforeEach
    void setUp() {
        MortgageConfigProperties config = new MortgageConfigProperties();
        config.setMaxLoanAmount(1_000_000L);
        config.setMaxMonthlyInterestRate(0.1); // 10%
        config.setMaxMaturityMonths(480); // 40 years
        validationService = new MortgageValidationService(config);
    }

    @ParameterizedTest(name = "Loan: {0}, Interest: {1}, Months: {2}")
    @CsvSource({
            "500000, 0.05, 360",
            "100000, 0.04, 120",
            "750000, 0.03, 240"
    })
    void shouldPassForValidInputs(Long loan, Double interest, Integer months) {
        assertDoesNotThrow(() ->
                validationService.validateInputs(loan, interest, months)
        );
    }

    @ParameterizedTest(name = "Invalid Loan: {0}")
    @CsvSource({
            "0",
            "-10000",
            "2000000"  // over max limit
    })
    void shouldThrowForInvalidLoanAmount(Long loan) {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                validationService.validateInputs(loan, 0.05, 360)
        );
        assertTrue(ex.getMessage().contains("Loan amount"));
    }

    @ParameterizedTest(name = "Invalid Interest: {0}")
    @CsvSource({
            "-0.01",
            "0.15"
    })
    void shouldThrowForInvalidInterestRate(Double interestRate) {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                validationService.validateInputs(500000L, interestRate, 360)
        );
        assertTrue(ex.getMessage().contains("interest rate"));
    }

    @ParameterizedTest(name = "Invalid Maturity: {0}")
    @CsvSource({
            "0",
            "-10",
            "500"
    })
    void shouldThrowForInvalidMaturity(Integer maturity) {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                validationService.validateInputs(500000L, 0.05, maturity)
        );
        assertTrue(ex.getMessage().contains("Maturity period"));
    }
}

