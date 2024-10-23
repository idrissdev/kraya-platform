CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    debt_id INT NOT NULL REFERENCES debts(debt_id),
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,  -- e.g., 'CASH', 'CREDIT_CARD', 'ONLINE'
    transaction_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);