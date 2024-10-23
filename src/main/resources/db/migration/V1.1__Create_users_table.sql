CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    role VARCHAR(20) NOT NULL,  -- e.g., 'DEBTOR', 'INSTITUTION', 'ADMIN'
    registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);