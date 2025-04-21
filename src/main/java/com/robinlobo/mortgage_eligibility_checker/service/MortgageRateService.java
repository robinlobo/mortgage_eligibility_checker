package com.robinlobo.mortgage_eligibility_checker.service;

import com.robinlobo.mortgage_eligibility_checker.model.MortgageConsultation;
import com.robinlobo.mortgage_eligibility_checker.model.MortgageRate;
import com.robinlobo.mortgage_eligibility_checker.dto.MortgageCalculationResult;
import com.robinlobo.mortgage_eligibility_checker.repo.MortgageRateRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MortgageRateService {

    @Autowired
    private MortgageRateRepo mortgageRateRepo;

    @Autowired
    private MortgageCalculatorService mortgageCalculatorService;

    // Get mortgage rates
    public List<MortgageRate> getAllMortgageRates() {
        return mortgageRateRepo.findAll();
    }

    // Check mortgage
    public MortgageCalculationResult checkMortgage(MortgageConsultation mortgageConsultation) {
        Long income = mortgageConsultation.income();
        Long homeValue = mortgageConsultation.homeValue();
        Long loanAmount = mortgageConsultation.loanAmount();
        Integer maturityPeriodInYears = mortgageConsultation.maturityPeriod();

        if (income == null || homeValue == null || loanAmount == null || maturityPeriodInYears == null) {
            throw new IllegalArgumentException("All input fields must be provided.");
        }

        List<MortgageRate> mortgageRateList = mortgageRateRepo.findByMaturityPeriod(maturityPeriodInYears);

        if (mortgageRateList == null || mortgageRateList.isEmpty()) {
            throw new IllegalArgumentException("No mortgage rate found for maturity period: " + maturityPeriodInYears);
        }

        MortgageRate selectedRate = mortgageRateList.getLast();
        Float annualInterestRate = selectedRate.getInterestRate();

        int maturityPeriodInMonths = maturityPeriodInYears * 12;
        double monthlyInterestRate = annualInterestRate / 100 / 12;

        double monthlyPayment = mortgageCalculatorService.calculateMonthlyMortgage(
                loanAmount, monthlyInterestRate, maturityPeriodInMonths
        );

        double totalMortgageAmount = monthlyPayment * maturityPeriodInMonths;
        double monthlyIncome = income / 12.0;

        // Business Rule 1: Monthly mortgage must not exceed 4× monthly income
        if (mortgageCalculatorService.isMortgageTooHigh(monthlyPayment, monthlyIncome)) {
            return new MortgageCalculationResult(false, monthlyPayment, totalMortgageAmount,
                    "Mortgage cannot exceed 4 times the monthly income");
        }

        // Business Rule 2: Total mortgage must not exceed home value
        if (mortgageCalculatorService.isMortgageOverHomeValue(totalMortgageAmount,homeValue)) {
            return new MortgageCalculationResult(false, monthlyPayment, totalMortgageAmount,
                    "Mortgage should not exceed the home value");
        }

        return new MortgageCalculationResult(true, monthlyPayment, totalMortgageAmount, null);
    }

    public MortgageRate addInterestRate(MortgageRate mortgageRate) {
        return mortgageRateRepo.save(mortgageRate);
    }
}
