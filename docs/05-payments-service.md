# Payments Service

## Goal

The Payments Service is responsible for validating and processing customer payments.

It receives payment requests after an order has been created.

Unlike the Orders Service, it has no responsibility for creating or managing orders.

This separation follows the Single Responsibility Principle and Microservices Architecture.

---

# Responsibilities

The Payments Service is responsible for:

- validating payment requests
- processing payments
- storing payment information
- publishing payment events
- notifying other services when a payment is completed

It never creates or modifies orders.

---

# Database

Table: payments

| Column | Type |
|---------|------|
| id | BIGINT |
| order_id | BIGINT |
| amount | DECIMAL |
| status | VARCHAR |
| payment_date | TIMESTAMP |

---

# REST API

Currently, the Payments Service exposes no public REST API.

It is event-driven.

Future administrative endpoints may include:

GET

/api/payments/{orderId}

Response

```json
{
  "orderId": 1,
  "amount": 1200,
  "status": "COMPLETED"
}
```

---

# Kafka Events

## Consumes

Topic

```
order-created
```

Event

```json
{
  "orderId": 1,
  "customerName": "Wilson",
  "productName": "IPHONE16",
  "quantity": 2,
  "price": 1200
}
```

---

## Publishes

Topic

```
payment-completed
```

Event

```json
{
  "paymentId": 1,
  "orderId": 1,
  "status": "COMPLETED"
}
```

---

# Business Rules

Each order generates only one payment.

A payment must reference an existing order.

Only successful payments publish the PaymentCompletedEvent.

---

# Domain Model

## Payment

Represents the payment associated with an order.

Each payment belongs to one and only one order.

The Payment entity stores:

- order identifier
- payment amount
- payment status
- payment date

---

## Payment Status

Possible values:

- PENDING
- COMPLETED
- FAILED

Payment starts in the PENDING state.

For this lab, every payment is automatically validated and moves to COMPLETED.

Later we will simulate payment failures and compensation using the Saga Pattern.

---

# Communication

Current workflow

```
Orders Service
        │
        ▼
OrderCreatedEvent
        │
        ▼
Payments Service
        │
        ▼
PaymentCompletedEvent
```

Later the Inventory Service will consume PaymentCompletedEvent to permanently decrease stock.

---

# Payment Processing

## Workflow

```
OrderCreatedEvent

        │

        ▼

Create Payment

        │

        ▼

Validate Payment

        │

        ▼

Persist Payment

        │

        ▼

Publish PaymentCompletedEvent (next iteration)
```

For now payment validation is simulated.

Every payment is considered successful.

Later we will introduce:

- payment failures
- retry policies
- payment gateway integration
- asynchronous confirmation

---

# Future Improvements

Later in the project we will introduce:

- Payment Gateway integration
- Retry mechanism
- Dead Letter Queue
- Saga Pattern
- Outbox Pattern
- Idempotency
- Observability
- Distributed Tracing

---

# Technology Stack

- Java 21
- Spring Boot
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Docker
- Maven

---

# Objective

At the end of this module we will have:

- Orders Service
- Payments Service

communicating asynchronously through Kafka events.

The Payments Service will persist payments and publish PaymentCompletedEvent for downstream services.