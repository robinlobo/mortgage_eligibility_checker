package com.robinlobo.mortgage_eligibility_checker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mortgage")
@Getter
@Setter
public class MortgageConfigProperties {

    private long maxLoanAmount;
    private double maxMonthlyInterestRate;
    private int maxMaturityMonths;
}

