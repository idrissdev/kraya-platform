CREATE TABLE debts (
    debt_id SERIAL PRIMARY KEY,
    debtor_id INT NOT NULL REFERENCES users(user_id),
    creditor_id INT NOT NULL REFERENCES institutions(institution_id),
    original_amount DECIMAL(10, 2) NOT NULL,
    current_amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(5, 2),  -- Optional
    due_date DATE,              -- Optional
    status VARCHAR(20) NOT NULL,  -- e.g., 'PENDING', 'ACTIVE', 'FORGIVEN', 'PAID'
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);