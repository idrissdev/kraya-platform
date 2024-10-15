-- V1__init.sql

-- This script initializes the database schema for the Kraya application.
-- Kraya is a platform for simplifying debt resolution, allowing debtors to explore
-- various repayment options and creditors to manage debts and collaborate on
-- identifying individuals who qualify for debt forgiveness.

-- Create sequences
-- These sequences are used to generate unique IDs for the corresponding tables.

CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE debt_id_seq;
CREATE SEQUENCE payment_id_seq;
CREATE SEQUENCE vote_id_seq;
CREATE SEQUENCE recommendation_id_seq;
CREATE SEQUENCE debt_transfer_id_seq;
CREATE SEQUENCE payment_plan_id_seq;
CREATE SEQUENCE document_id_seq;

-- Create the base user table
-- This table stores information about all users in the Kraya application,
-- including debtors, creditors, associations, and administrators.

CREATE TABLE app_user (
    -- Unique ID for the user, generated from the user_id_seq sequence
                          user_id INT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    -- Unique username for the user
                          username VARCHAR(255) NOT NULL UNIQUE,
    -- Password for the user account (should be securely hashed in a real application)
                          password VARCHAR(255) NOT NULL,
    -- Unique email address for the user
                          email VARCHAR(255) NOT NULL UNIQUE,
    -- First name of the user
                          first_name VARCHAR(255),
    -- Last name of the user
                          last_name VARCHAR(255),
    -- Role of the user (e.g., 'DEBTOR', 'CREDITOR', 'ASSOCIATION', 'ADMIN')
                          role VARCHAR(50) NOT NULL,
    -- Date and time when the user registered on the platform
                          registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Create subclass tables for different user types

-- Debtor table
-- This table stores additional information specific to debtors.
-- Each debtor is also a user in the app_user table.

CREATE TABLE debtor (
    -- ID of the debtor, referencing the app_user table
                        debtor_id INT PRIMARY KEY REFERENCES app_user(user_id),
    -- Phone number of the debtor
                        phone_number VARCHAR(20),
    -- Address of the debtor
                        address TEXT,
    -- Date of birth of the debtor
                        date_of_birth DATE,
    -- Income level of the debtor (e.g., 'LOW', 'MEDIUM', 'HIGH')
                        income_level VARCHAR(50),
    -- Employment status of the debtor (e.g., 'EMPLOYED', 'UNEMPLOYED', 'SELF-EMPLOYED')
                        employment_status VARCHAR(50),
    -- Reason for the debtor's debt
                        debt_reason TEXT,
    -- Marital status of the debtor (e.g., 'SINGLE', 'MARRIED', 'DIVORCED')
                        marital_status VARCHAR(50),
    -- Number of dependents the debtor supports
                        dependents_number INT,
    -- Housing status of the debtor (e.g., 'OWN', 'RENT', 'HOMELESS')
                        housing_status VARCHAR(50),
    -- Detailed description of the debtor's financial difficulties
                        financial_difficulties TEXT,
    -- Indicates if the debtor's profile information has been verified
                        profile_verified BOOLEAN DEFAULT FALSE,
    -- Gender of the debtor (e.g., 'MALE', 'FEMALE', 'OTHER')
                        gender VARCHAR(50),
    -- Preferred language for communication with the debtor
                        preferred_language VARCHAR(50)
);

-- Creditor table
-- This table stores additional information specific to creditors.
-- Each creditor is also a user in the app_user table.

CREATE TABLE creditor (
    -- ID of the creditor, referencing the app_user table
                          creditor_id INT PRIMARY KEY REFERENCES app_user(user_id),
    -- Contact person for the creditor
                          contact_person VARCHAR(255),
    -- Phone number of the creditor
                          phone_number VARCHAR(20),
    -- Address of the creditor
                          address TEXT,
    -- Website of the creditor
                          website VARCHAR(255),
    -- Indicates if the creditor has been verified by Kraya
                          verified BOOLEAN DEFAULT FALSE,
    -- Credit rating of the creditor (e.g., 'EXCELLENT', 'GOOD', 'FAIR', 'POOR')
                          credit_rating VARCHAR(50),
    -- Number of years the creditor has been in business
                          years_in_business INT,
    -- Business license ID/number of the creditor
                          business_license VARCHAR(255),
    -- Unique API key for the creditor to access the Kraya API
                          api_key VARCHAR(255) UNIQUE
);

-- Association table
-- This table stores additional information specific to associations (e.g., charities, non-profits).
-- Each association is also a user in the app_user table.

CREATE TABLE association (
    -- ID of the association, referencing the app_user table
                             association_id INT PRIMARY KEY REFERENCES app_user(user_id),
    -- Area of focus for the association (e.g., 'POVERTY RELIEF', 'EDUCATION')
                             area_of_focus VARCHAR(255),
    -- Tax ID of the association
                             tax_id VARCHAR(255),
    -- Registration number of the association
                             registration_number VARCHAR(255)
);

-- Create other tables

-- Debt table
-- This table stores information about debts owed by debtors to creditors.

CREATE TABLE debt (
    -- Unique ID for the debt, generated from the debt_id_seq sequence
                      debt_id INT PRIMARY KEY DEFAULT nextval('debt_id_seq'),
    -- ID of the debtor associated with the debt, referencing the app_user table
                      debtor_id INT NOT NULL REFERENCES app_user(user_id),
    -- ID of the creditor associated with the debt, referencing the app_user table
                      creditor_id INT NOT NULL REFERENCES app_user(user_id),
    -- Original amount of the debt
                      original_amount DECIMAL(10, 2) NOT NULL,
    -- Current remaining amount of the debt
                      current_amount DECIMAL(10, 2) NOT NULL,
    -- Interest rate applied to the debt (if applicable)
                      interest_rate DECIMAL(5, 2),
    -- Due date for the debt (if applicable)
                      due_date DATE,
    -- Current status of the debt (e.g., 'PENDING', 'ACTIVE', 'PAID', 'FORGIVEN', 'CANCELLED', 'DISPUTED')
                      status VARCHAR(50) NOT NULL,
    -- Date and time when the debt was created
                      creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Payment table
-- This table stores information about payments made towards debts.

CREATE TABLE payment (
    -- Unique ID for the payment, generated from the payment_id_seq sequence
                         payment_id INT PRIMARY KEY DEFAULT nextval('payment_id_seq'),
    -- ID of the debt this payment is associated with, referencing the debt table
                         debt_id INT NOT NULL REFERENCES debt(debt_id),
    -- Amount of the payment
                         amount DECIMAL(10, 2) NOT NULL,
    -- Method used for the payment (e.g., 'CREDIT CARD', 'BANK TRANSFER')
                         payment_method VARCHAR(50) NOT NULL,
    -- Date and time when the payment was made
                         transaction_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Vote table
-- This table stores votes cast by creditors for or against debt forgiveness for debtors.

CREATE TABLE Vote (
    -- Unique ID for the vote, generated from the vote_id_seq sequence
                      vote_id INT PRIMARY KEY DEFAULT nextval('vote_id_seq'),
    -- ID of the debtor being voted on, referencing the app_user table
                      debtor_id INT REFERENCES app_user(user_id) NOT NULL,
    -- ID of the creditor casting the vote, referencing the app_user table
                      creditor_id INT REFERENCES app_user(user_id) NOT NULL,
    -- The vote itself (true = for forgiveness, false = against)
                      vote BOOLEAN NOT NULL,
    -- Date and time when the vote was cast
                      vote_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Optional comment from the creditor regarding their vote
                      comment TEXT
);

-- Recommendation table
-- This table stores recommendations made by creditors for debt forgiveness for debtors.

CREATE TABLE Recommendation (
    -- Unique ID for the recommendation, generated from the recommendation_id_seq sequence
                                recommendation_id INT PRIMARY KEY DEFAULT nextval('recommendation_id_seq'),
    -- ID of the debtor being recommended, referencing the app_user table
                                debtor_id INT REFERENCES app_user(user_id) NOT NULL,
-- ID of the creditor making the recommendation, referencing the app_user table