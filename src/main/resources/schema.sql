CREATE TABLE IF NOT EXISTS MORTGAGE_RATE(
  ID INT PRIMARY KEY AUTO_INCREMENT,
  MATURITY_PERIOD INT not null,
  INTEREST_RATE FLOAT,
  CREATED_ON TIMESTAMP WITH TIME ZONE,
  LAST_UPDATED_ON TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS MORTGAGE_RATE_RECORD(
  ID INT PRIMARY KEY AUTO_INCREMENT,
  MATURITY_PERIOD INT not null,
  INTEREST_RATE FLOAT,
  CREATED_ON TIMESTAMP WITH TIME ZONE,
  LAST_UPDATED_ON TIMESTAMP WITH TIME ZONE
);