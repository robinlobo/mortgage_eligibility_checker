package com.robinlobo.mortgage_eligibility_checker.service;

import com.robinlobo.mortgage_eligibility_checker.model.MortgageRate;
import com.robinlobo.mortgage_eligibility_checker.repo.MortgageRateRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MortgageRateServiceTest {

    @Mock
    private MortgageRateRepo mortgageRateRepo;

    @InjectMocks
    private MortgageRateService mortgageRateService;

    private MortgageRate givenRate;
    private MortgageRate savedRate;
    private final Instant testInstant = Instant.now();

    @BeforeEach
    void setup() {
        givenRate = MortgageRate.builder()
                .id(1L)
                .maturityPeriod(10)
                .interestRate(0.5f)
                .createdOn(testInstant)
                .lastUpdatedOn(testInstant)
                .build();
        savedRate = MortgageRate.builder()
                .id(1L)
                .maturityPeriod(10)
                .interestRate(0.5f)
                .createdOn(testInstant)
                .lastUpdatedOn(testInstant)
                .build();
    }

    @Test
    void whenGetAllRates_thenReturnAllInterestRates() {
        List<MortgageRate> rates = List.of(savedRate);
        when(mortgageRateRepo.findAll()).thenReturn(rates);
        List<MortgageRate> result = mortgageRateService.getAllMortgageRates();
        assertEquals(rates, result, "Should return all interest rates");
    }
}
