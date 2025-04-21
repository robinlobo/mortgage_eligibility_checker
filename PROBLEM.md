# Backend Assessment

## Application

We would like you to create a Java-based backend application using REST.  
It should contain the following endpoints:

- `GET /api/interest-rates`  
  Retrieves a list of current interest rates.

- `POST /api/mortgage-check`  
  Accepts parameters to calculate and evaluate a mortgage check.

## Requirements

- The list of current mortgage rates should be created in-memory on application startup.
- A mortgage rate object contains the following fields:
    - `maturityPeriod` (Integer)
    - `interestRate` (Percentage)
    - `lastUpdate` (Timestamp)
- The input for the mortgage check must include at least:
    - `income` (Amount)
    - `maturityPeriod` (Integer)
    - `loanValue` (Amount)
    - `homeValue` (Amount)
- The response should indicate whether the mortgage is feasible (`boolean`) and the monthly cost (`Amount`) of the mortgage.

## Business Rules

- A mortgage should **not exceed 4 times** the applicant’s monthly income.
- A mortgage should **not exceed the value** of the home.

## Implementation Notes

Treat this application as a real MVP (Minimum Viable Product) ready for production.  
You may use any frameworks or libraries you prefer to build and test the application.

## Estimated Duration

This assignment is expected to take **4-5 hours** to complete.

---

Good luck!
