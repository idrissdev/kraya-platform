CREATE TABLE institutions (
    institution_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,  -- e.g., 'CHARITY', 'RELIGIOUS', 'BUSINESS'
    contact_info TEXT,          -- Can store address, phone, etc.
    registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);