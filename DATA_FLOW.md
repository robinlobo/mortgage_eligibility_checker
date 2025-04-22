## Design Breakdown

### `MortgageCalculatorService`

- Validates input data.
- Calculates monthly mortgage using amortization logic.
- Delegates rule checks to `MortgageRulesService`.

### `MortgageRulesService`

- Contains and isolates all business rule logic.

### `MortgageRateService`

- Handles fetching and storing mortgage rate data.

### `DTOs`

- `MortgageConsultation`: Input payload for mortgage checks.
- `MortgageCalculationResult`: Result returned to client, including feasibility and financials.

---

```mermaid
flowchart TD
A[Client Request] -->|POST /api/mortgage-check| B[MortgageRateService]
B --> C[Fetch rates from in-memory DB]
C --> D[Select rate for given maturityPeriod]
D --> E[Calculate monthlyInterestRate]
E --> F[Call MortgageCalculatorService]

    subgraph Calculation & Validation
        F --> G[validateInputs]
        G --> H[Amortization Formula or Simple Division]
        H --> I[Calculate monthlyPayment & totalMortgage]
        I --> J[MortgageRulesService]

        J --> J1[Check: monthlyPayment > income × 4]
        J --> J2[Check: totalMortgage ≥ homeValue]
    end

    J1 -->|If true| K1[Return: Rejected - Income rule]
    J2 -->|If true| K2[Return: Rejected - Home value rule]
    J1 -->|If false| J2
    J2 -->|If false| L[Return: Accepted + Payment Info]

    B -->|GET /api/interest-rates| M[Return list of Mortgage Rates]

    style B fill:#f9f,stroke:#333,stroke-width:1px
    style F fill:#bbf,stroke:#333,stroke-width:1px
    style J fill:#bfb,stroke:#333,stroke-width:1px
```


