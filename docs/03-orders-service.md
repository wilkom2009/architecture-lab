# Orders Service

## Responsibility

Manage customer orders.

## Features

- Create an order
- Retrieve all orders
- Retrieve an order by ID
- Delete an order

## Package Structure

```
controller
service
repository
entity
dto
mapper
```

## Request Flow

```
HTTP Request
      │
      ▼
Controller
      │
      ▼
Service
      │
      ▼
Mapper
      │
      ▼
Repository
      │
      ▼
PostgreSQL
      │
      ▼
Response DTO
```

## Technologies

- Spring Boot
- Spring Data JPA
- PostgreSQL
- MapStruct
- Lombok

## Design Decisions

- DTOs isolate the REST API from the persistence layer.
- MapStruct is used to avoid manual mapping.
- Repository only accesses the database.
- Business logic belongs to the service layer.
- Controller contains no business logic.