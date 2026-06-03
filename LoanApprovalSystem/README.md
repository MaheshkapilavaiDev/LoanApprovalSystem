# Loan Approval Workflow System

## Overview

The Loan Approval Workflow System is a Spring Boot backend application that automates the loan processing lifecycle. The system supports loan submission, verification, risk assessment, approval/rejection decisions, audit logging, and workflow tracking.

## Features

* Submit Loan Application
* Loan Verification
* Risk Assessment
* Loan Approval
* Loan Rejection
* Workflow State Tracking
* Audit Logging
* Idempotent Request Handling
* Transaction Management
* Validation Using Bean Validation

## Technology Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok

## Workflow

```text
SUBMITTED
    ↓
VERIFIED
    ↓
RISK_ASSESSMENT
    ↓
MANUAL_REVIEW
    ↓
APPROVED / REJECTED
```

## Database Entities

### LoanApplication

Stores loan application details.

Fields:

* id
* applicantName
* email
* loanAmount
* annualIncome
* state
* idempotencyKey

### Verification

Stores verification details for a loan application.

Fields:

* id
* verifiedBy
* remarks
* loanApplication

### Approval

Stores approval or rejection decision.

Fields:

* id
* decision
* reason
* riskScore
* loanApplication

### AuditLog

Stores workflow transition history.

Fields:

* id
* loanId
* fromState
* toState
* actionTime

## API Endpoints

### Submit Loan

POST /api/loans/submit

### Verify Loan

PUT /api/loans/{id}/verify

### Risk Assessment

PUT /api/loans/{id}/risk

### Approve Loan

PUT /api/loans/{id}/approve

### Reject Loan

PUT /api/loans/{id}/reject

### Get Loan Details

GET /api/loans/{id}

## Business Rules

* Loan must be verified before risk assessment.
* Loan can only move through valid workflow states.
* High-risk loans are rejected.
* Low-risk loans can be approved.
* Manual review is required for medium-risk loans.
* Duplicate submissions are prevented using idempotency keys.

## Transaction Management

All critical workflow operations are executed within database transactions to maintain consistency.

## Audit Logging

Every workflow transition is recorded for traceability and compliance.

Examples:

* SUBMITTED → VERIFIED
* VERIFIED → RISK_ASSESSMENT
* MANUAL_REVIEW → APPROVED
* MANUAL_REVIEW → REJECTED

## Running the Application

1. Configure MySQL database.
2. Update application.properties.
3. Run Maven build.

```bash
mvn clean install
```

4. Start the application.

```bash
mvn spring-boot:run
```

## Author

Mahesh Kapilavai
