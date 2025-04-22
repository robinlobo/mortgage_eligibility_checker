package com.robinlobo.mortgage_eligibility_checker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "MORTGAGE_RATE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MortgageRate {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MATURITY_PERIOD")
    private Integer maturityPeriod;

    @Column(name = "INTEREST_RATE")
    private Float interestRate;

    @Column(name = "CREATED_ON")
    private Instant createdOn;

    @Column(name = "LAST_UPDATED_ON")
    private Instant lastUpdatedOn;
}
