package com.robinlobo.mortgage_eligibility_checker.controller;

import com.robinlobo.mortgage_eligibility_checker.model.MortgageConsultation;
import com.robinlobo.mortgage_eligibility_checker.model.MortgageRate;
import com.robinlobo.mortgage_eligibility_checker.dto.MortgageCalculationResult;
import com.robinlobo.mortgage_eligibility_checker.service.MortgageRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MortgageRateController {

    @Autowired
    private MortgageRateService mortgageRateService;

    // Get all interest rates
    @GetMapping("/api/interest-rates")
    public List<MortgageRate> getMortgageRates() {
        return mortgageRateService.getAllMortgageRates();
    }

    // Get all interest rates
    @PostMapping("/api/interest-rates")
    @CacheEvict(value = "interest_rates", allEntries = true)
    public ResponseEntity<MortgageRate> addBook(@RequestBody MortgageRate mortgageRate) {
        MortgageRate newMortgageRate = mortgageRateService.addInterestRate(mortgageRate);
        return ResponseEntity.ok(newMortgageRate);
    }

    // Add a new user
    @PostMapping("/api/mortgage-check")
    public MortgageCalculationResult checkMortgageRate(@Valid @RequestBody MortgageConsultation mortgageConsultation) {
        return mortgageRateService.checkMortgage(mortgageConsultation);
    }
}
