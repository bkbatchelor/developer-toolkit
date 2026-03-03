# Product Guide

## Initial Concept
A Gradle 9.x.x monorepo comparing SQL (PostgreSQL) and NoSQL (MongoDB) architectures using Java 21 and Spring Boot 3.5.x.

---

# Developer Toolbox (DT)

## Vision & Mission
To equip developers with a comparative understanding of SQL vs. NoSQL architectures and provide a high-quality boilerplate for Java 21/Spring Boot 3.5.x ecosystems.

## Target Users
- **Developers:** Looking to compare Java object mapping for Tables (SQL) vs. Documents (NoSQL).
- **Engineering Leads:** Needing a reference for when to use Spring Data JPA vs. Spring Data MongoDB.

## MVP Scope
- **Gradle Multi-Project Setup:** Centralized versioning via `libs.versions.toml`.
- **SQL Module:** PostgreSQL implementation using JPA/Hibernate and Liquibase.
- **NoSQL Module:** MongoDB implementation using Spring Data MongoDB.
- **Common API:** Shared DTOs and interfaces to ensure API consistency, utilizing **Lombok** for reduced boilerplate.
- **Testing Suite:** Integration tests using **Testcontainers** for both database implementations.
- **Validation:** Implementation of **Jakarta Bean Validation (Standard Annotations)** at the API layer.

## Core Architecture
- `persistence-sql/`: JPA entities, repositories, and Liquibase change-logs.
- `persistence-nosql/`: MongoDB documents and repositories.
- `common-api/`: Shared DTOs (Data Transfer Objects) and interfaces.

## Deployment & CI/CD
- **Continuous Integration:** **GitHub Actions** will be used for automated build and test verification.
- **Containerization:** Support for running database containers (Testcontainers) during integration testing.

## Functional Requirements: Product Catalog
The toolbox will implement a standard Product Catalog with two parallel storage implementations:
- **Relational Storage:** Normalized tables for products, categories, and suppliers.
- **Document Storage:** Nested documents for products with embedded attributes.
- **Querying:** Support for complex joins (SQL) and aggregation pipelines (NoSQL).
