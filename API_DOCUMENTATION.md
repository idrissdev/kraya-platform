# Kraya Platform API Documentation

## Overview

The Kraya Platform API provides a comprehensive REST API for managing debt resolution, payments, and debt forgiveness processes.

**Base URL**: `http://localhost:8080/api`

**API Documentation (Swagger)**: `http://localhost:8080/swagger-ui.html`

## Authentication

Most endpoints require HTTP Basic Authentication. Include credentials in the Authorization header:

```
Authorization: Basic base64(username:password)
```

## Public Endpoints

The following endpoints do not require authentication:

- `POST /api/users/register` - User registration
- `POST /api/debtors` - Debtor registration
- `POST /api/creditors` - Creditor registration
- `POST /api/associations` - Association registration
- `GET /api/health` - Health check

## API Endpoints

### 1. Health Check

#### Check API Health
```http
GET /api/health
```

**Response** (200 OK):
```json
{
  "status": "UP",
  "timestamp": "2025-11-20T10:00:00",
  "service": "Kraya Platform API",
  "version": "1.0.0"
}
```

---

### 2. User Management

#### Register User
```http
POST /api/users/register
```

**Request Body**:
```json
{
  "username": "john_doe",
  "password": "password123",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "profilePictureUrl": "https://example.com/profile.jpg"
}
```

**Response** (201 Created):
```json
{
  "userId": 1,
  "message": "User registered successfully"
}
```

#### Get User by ID
```http
GET /api/users/{userId}
```

**Response** (200 OK):
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "status": "ACTIVE",
  "registrationDate": "2025-11-20T10:00:00"
}
```

#### Update User
```http
PUT /api/users/{userId}
```

#### Delete User
```http
DELETE /api/users/{userId}
```

---

### 3. Debtor Management

#### Register Debtor
```http
POST /api/debtors
```

**Request Body**:
```json
{
  "username": "debtor1",
  "password": "password123",
  "email": "debtor@example.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "phoneNumber": "+1234567890",
  "address": "123 Main St",
  "dateOfBirth": "1990-01-15",
  "incomeLevel": "50000",
  "employmentStatus": "EMPLOYED",
  "debtReason": "Medical expenses",
  "maritalStatus": "SINGLE",
  "dependentsNumber": 0,
  "housingStatus": "RENTING",
  "financialDifficulties": "Unable to pay medical bills",
  "profileVerified": false,
  "gender": "FEMALE",
  "preferredLanguage": "English"
}
```

**Response** (201 Created):
```json
{
  "userId": 2,
  "message": "Debtor registered successfully"
}
```

#### Get All Debtors
```http
GET /api/debtors
```

#### Get Debtor by ID
```http
GET /api/debtors/{debtorId}
```

#### Update Debtor
```http
PUT /api/debtors/{debtorId}
```

#### Delete Debtor
```http
DELETE /api/debtors/{debtorId}
```

---

### 4. Creditor Management

#### Register Creditor
```http
POST /api/creditors
```

**Request Body**:
```json
{
  "username": "creditor1",
  "password": "password123",
  "email": "creditor@example.com",
  "firstName": "John",
  "lastName": "Bank",
  "contactPerson": "Jane Manager",
  "phoneNumber": "+1234567890",
  "address": "456 Business Ave",
  "website": "https://creditor.com",
  "verified": false,
  "creditRating": "AAA",
  "yearsInBusiness": 25,
  "businessLicense": "LIC-12345",
  "apiKey": "api_key_here"
}
```

**Response** (201 Created):
```json
{
  "userId": 3,
  "message": "Creditor registered successfully"
}
```

#### Get All Creditors
```http
GET /api/creditors
```

#### Get Creditor by ID
```http
GET /api/creditors/{creditorId}
```

#### Update Creditor
```http
PUT /api/creditors/{creditorId}
```

#### Delete Creditor
```http
DELETE /api/creditors/{creditorId}
```

---

### 5. Association Management

#### Register Association
```http
POST /api/associations
```

**Request Body**:
```json
{
  "username": "charity1",
  "password": "password123",
  "email": "info@charity.org",
  "firstName": "Helping",
  "lastName": "Hands",
  "areaOfFocus": "Poverty relief",
  "taxId": "TAX-12345",
  "registrationNumber": "REG-67890"
}
```

**Response** (201 Created):
```json
{
  "userId": 4,
  "message": "Association registered successfully"
}
```

#### Get All Associations
```http
GET /api/associations
```

#### Get Association by ID
```http
GET /api/associations/{associationId}
```

#### Update Association
```http
PUT /api/associations/{associationId}
```

#### Delete Association
```http
DELETE /api/associations/{associationId}
```

---

### 6. Debt Management

#### Create Debt
```http
POST /api/debts
```

**Request Body**:
```json
{
  "debtorId": 2,
  "creditorId": 3,
  "originalAmount": 10000.00,
  "currentAmount": 10000.00,
  "interestRate": 5.5,
  "dueDate": "2026-01-01",
  "status": "ACTIVE"
}
```

**Response** (201 Created):
```json
{
  "debtId": 1,
  "message": "Debt created successfully"
}
```

#### Get All Debts
```http
GET /api/debts
```

#### Get Debt by ID
```http
GET /api/debts/{debtId}
```

#### Get Debts by Debtor
```http
GET /api/debts/debtor/{debtorId}
```

#### Get Debts by Creditor
```http
GET /api/debts/creditor/{creditorId}
```

#### Get Debts by Status
```http
GET /api/debts/status/{status}
```

**Status values**: `ACTIVE`, `PAID`, `DEFAULTED`, `FORGIVEN`

#### Update Debt
```http
PUT /api/debts/{debtId}
```

#### Delete Debt
```http
DELETE /api/debts/{debtId}
```

---

### 7. Payment Management

#### Create Payment
```http
POST /api/payments
```

**Request Body**:
```json
{
  "debtId": 1,
  "amount": 500.00,
  "paymentMethod": "CREDIT_CARD"
}
```

**Response** (201 Created):
```json
{
  "paymentId": 1,
  "message": "Payment processed successfully"
}
```

#### Get All Payments
```http
GET /api/payments
```

#### Get Payment by ID
```http
GET /api/payments/{paymentId}
```

#### Get Payments by Debt
```http
GET /api/payments/debt/{debtId}
```

#### Delete Payment
```http
DELETE /api/payments/{paymentId}
```

---

### 8. Payment Plan Management

#### Create Payment Plan
```http
POST /api/payment-plans
```

**Request Body**:
```json
{
  "debtId": 1,
  "creditorId": 3,
  "planType": "MONTHLY",
  "installmentAmount": 200.00,
  "startDate": "2025-12-01",
  "endDate": "2027-12-01",
  "status": "ACTIVE"
}
```

**Response** (201 Created):
```json
{
  "planId": 1,
  "message": "Payment plan created successfully"
}
```

#### Get All Payment Plans
```http
GET /api/payment-plans
```

#### Get Payment Plan by ID
```http
GET /api/payment-plans/{planId}
```

#### Get Payment Plans by Debt
```http
GET /api/payment-plans/debt/{debtId}
```

#### Update Payment Plan
```http
PUT /api/payment-plans/{planId}
```

#### Delete Payment Plan
```http
DELETE /api/payment-plans/{planId}
```

---

### 9. Debt Transfer Management

#### Create Debt Transfer
```http
POST /api/debt-transfers
```

**Request Body**:
```json
{
  "debtId": 1,
  "fromCreditorId": 3,
  "toCreditorId": 4,
  "amount": 5000.00,
  "status": "PENDING"
}
```

**Response** (201 Created):
```json
{
  "transferId": 1,
  "message": "Debt transfer created successfully"
}
```

#### Get All Debt Transfers
```http
GET /api/debt-transfers
```

#### Get Debt Transfer by ID
```http
GET /api/debt-transfers/{transferId}
```

#### Get Debt Transfers by Debt
```http
GET /api/debt-transfers/debt/{debtId}
```

#### Update Debt Transfer
```http
PUT /api/debt-transfers/{transferId}
```

#### Delete Debt Transfer
```http
DELETE /api/debt-transfers/{transferId}
```

---

### 10. Recommendation Management

#### Create Recommendation
```http
POST /api/recommendations
```

**Request Body**:
```json
{
  "debtorId": 2,
  "creditorId": 3,
  "comment": "This debtor has shown good faith in payments and deserves consideration for debt forgiveness."
}
```

**Response** (201 Created):
```json
{
  "recommendationId": 1,
  "message": "Recommendation created successfully"
}
```

#### Get All Recommendations
```http
GET /api/recommendations
```

#### Get Recommendation by ID
```http
GET /api/recommendations/{recommendationId}
```

#### Get Recommendations by Debtor
```http
GET /api/recommendations/debtor/{debtorId}
```

#### Update Recommendation
```http
PUT /api/recommendations/{recommendationId}
```

#### Delete Recommendation
```http
DELETE /api/recommendations/{recommendationId}
```

---

### 11. Vote Management

#### Create Vote
```http
POST /api/votes
```

**Request Body**:
```json
{
  "debtorId": 2,
  "creditorId": 3,
  "vote": true,
  "comment": "I support debt forgiveness for this debtor."
}
```

**Response** (201 Created):
```json
{
  "voteId": 1,
  "message": "Vote created successfully"
}
```

#### Get All Votes
```http
GET /api/votes
```

#### Get Vote by ID
```http
GET /api/votes/{voteId}
```

#### Get Votes by Debtor
```http
GET /api/votes/debtor/{debtorId}
```

#### Update Vote
```http
PUT /api/votes/{voteId}
```

#### Delete Vote
```http
DELETE /api/votes/{voteId}
```

---

### 12. Document Management

#### Upload Document
```http
POST /api/documents
```

**Request Body**:
```json
{
  "debtorId": 2,
  "documentType": "INCOME_PROOF",
  "documentPath": "/uploads/documents/proof_of_income.pdf",
  "verificationStatus": "PENDING"
}
```

**Response** (201 Created):
```json
{
  "documentId": 1,
  "message": "Document uploaded successfully"
}
```

#### Get All Documents
```http
GET /api/documents
```

#### Get Document by ID
```http
GET /api/documents/{documentId}
```

#### Get Documents by Debtor
```http
GET /api/documents/debtor/{debtorId}
```

#### Update Document
```http
PUT /api/documents/{documentId}
```

#### Delete Document
```http
DELETE /api/documents/{documentId}
```

---

### 13. Role Management

#### Create Role
```http
POST /api/roles
```

**Request Body**:
```json
{
  "name": "ADMIN",
  "description": "Administrator role with full access"
}
```

#### Get All Roles
```http
GET /api/roles
```

#### Get Role by ID
```http
GET /api/roles/{roleId}
```

#### Update Role
```http
PUT /api/roles/{roleId}
```

#### Delete Role
```http
DELETE /api/roles/{roleId}
```

---

## Error Responses

### 400 Bad Request
```json
{
  "message": "Invalid input data"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Authentication required"
}
```

### 404 Not Found
```json
{
  "message": "Resource not found with ID: 123"
}
```

### 409 Conflict
```json
{
  "message": "Username already exists"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Deployment

### Using Docker Compose

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### Environment Variables

Configure the following environment variables for production:

- `SPRING_PROFILES_ACTIVE` - Active profile (dev, staging, prod)
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `DB_USERNAME` - Database username (used in properties files)
- `DB_PASSWORD` - Database password (used in properties files)
- `ADMIN_USERNAME` - Admin username for security
- `ADMIN_PASSWORD` - Admin password for security

---

## Testing

Access the Swagger UI for interactive API testing:

**URL**: `http://localhost:8080/swagger-ui.html`

Or use curl:

```bash
# Health check
curl http://localhost:8080/api/health

# Register a debtor
curl -X POST http://localhost:8080/api/debtors \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testdebtor",
    "password": "password123",
    "email": "test@example.com",
    "firstName": "Test",
    "lastName": "Debtor"
  }'

# Get all debts (with authentication)
curl -u username:password http://localhost:8080/api/debts
```

---

## Support

For support, please contact:
- Email: slimani@slimatech.com
- Website: https://www.kraya.com
