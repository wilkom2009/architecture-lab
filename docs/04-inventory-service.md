# Inventory Service

## Goal

The Inventory Service is responsible for managing product stock.

Unlike the Orders Service, it has no knowledge of customers or orders.

Its only responsibility is inventory management.

This separation follows the Single Responsibility Principle and Microservices Architecture.

---

# Responsibilities

The Inventory Service is responsible for:

- maintaining product stock
- checking product availability
- reserving stock
- releasing reserved stock
- decreasing stock after payment
- increasing stock after cancellation or refund

It never creates or manages orders.

---

# Database

Table: inventory

| Column | Type |
|---------|------|
| id | BIGINT |
| product_code | VARCHAR |
| quantity | INTEGER |
| reserved_quantity | INTEGER |

---

# REST API

## Get inventory

GET

/api/inventory/{productCode}

Response

```json
{
  "productCode": "IPHONE-15",
  "availableQuantity": 20,
  "reservedQuantity": 5
}
```

---

## Reserve stock

POST

/api/inventory/reserve

```json
{
  "productCode": "IPHONE-15",
  "quantity": 2
}
```

---

## Release reservation

POST

/api/inventory/release

```json
{
  "productCode": "IPHONE-15",
  "quantity": 2
}
```

---

## Decrease stock

POST

/api/inventory/decrease

```json
{
  "productCode": "IPHONE-15",
  "quantity": 2
}
```

---

## Increase stock

POST

/api/inventory/increase

```json
{
  "productCode": "IPHONE-15",
  "quantity": 2
}
```

---

# Business Rules

A reservation cannot exceed the available stock.

Available Stock = Quantity − Reserved Quantity

If the requested quantity exceeds the available stock, the service returns HTTP 409 Conflict.

---

# Communication

Orders Service will call Inventory Service before creating an order.

Future communication:

Orders Service

↓

Inventory Service

↓

Stock Validation

---

# Future Improvements

Later in the project we will introduce:

- Kafka Events
- Saga Pattern
- Distributed Transactions
- Event Sourcing
- CQRS

For now communication remains synchronous using REST APIs.

---

# Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- MapStruct
- Docker
- Maven

---

# Objective

At the end of this module we will have two independent microservices:

- Orders Service
- Inventory Service

communicating together through REST.