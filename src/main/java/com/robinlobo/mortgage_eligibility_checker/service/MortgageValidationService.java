package com.robinlobo.mortgage_eligibility_checker.service;

import com.robinlobo.mortgage_eligibility_checker.config.MortgageConfigProperties;
import com.robinlobo.mortgage_eligibility_checker.model.MortgageConsultation;
import org.springframework.stereotype.Service;

@Service
public class MortgageValidationService {

    private final MortgageConfigProperties config;

    public MortgageValidationService(MortgageConfigProperties config) {
        this.config = config;
    }

    public void validateInputs(Long loanAmount, Double monthlyInterestRate, Integer maturityPeriodInMonths) {
        if (loanAmount == null || monthlyInterestRate == null || maturityPeriodInMonths == null) {
            throw new IllegalArgumentException("Loan amount, interest rate, and maturity period must not be null.");
        }

        if (loanAmount <= 0 || loanAmount > config.getMaxLoanAmount()) {
            throw new IllegalArgumentException("Loan amount must be > 0 and <= $" + config.getMaxLoanAmount());
        }

        if (monthlyInterestRate < 0 || monthlyInterestRate > config.getMaxMonthlyInterestRate()) {
            throw new IllegalArgumentException("Monthly interest rate is invalid.");
        }

        if (maturityPeriodInMonths <= 0 || maturityPeriodInMonths > config.getMaxMaturityMonths()) {
            throw new IllegalArgumentException("Maturity period must be within allowed bounds.");
        }
    }

    public boolean validateConsultationInputs(MortgageConsultation consultation) {
        if (consultation.income() == null ||
                consultation.homeValue() == null ||
                consultation.loanAmount() == null ||
                consultation.maturityPeriod() == null) {
            throw new IllegalArgumentException("All input fields must be provided.");
        }
        return true;
    }
}

