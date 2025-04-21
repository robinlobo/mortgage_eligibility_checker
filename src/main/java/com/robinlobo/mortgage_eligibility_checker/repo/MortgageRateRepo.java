package com.robinlobo.mortgage_eligibility_checker.repo;

import com.robinlobo.mortgage_eligibility_checker.model.MortgageRate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MortgageRateRepo extends JpaRepository<MortgageRate, Long> {
    @Override
    @Cacheable( "interest_rates")
    Optional<MortgageRate> findById(Long id);

    @Query("SELECT t FROM MortgageRate t WHERE t.maturityPeriod = ?1")
    List<MortgageRate> findByMaturityPeriod(int maturityPeriod);

}
