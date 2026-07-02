# Project Architecture

## Goal

Build a cloud-native e-commerce platform based on microservices and modern backend practices.

## Architecture

```
                 API Gateway
                      |
    -----------------------------------------
    |         |         |         |          |
 Orders   Customers  Inventory  Payment  Notification
    |         |         |         |          |
    -----------------------------------------
                      |
                 PostgreSQL
```

## Parent Project

The project uses a Maven multi-module architecture.

```
shopflow-parent
│
├── orders-service
├── customers-service
├── inventory-service
├── payment-service
└── notification-service
```

## Design Principles

- Single Responsibility
- Domain-driven design (light)
- REST communication
- Event-driven architecture
- Stateless services
- Docker-first
- Cloud-ready

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Kubernetes
- AWS