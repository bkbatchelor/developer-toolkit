# Developer Toolbox (DT) - Product Requirement Document

## 1. Executive Summary

The **Developer Toolbox (DT)** is a centralized GitHub repository providing "production-standard" code examples in a **Gradle 9.x.x monorepo**. It specifically highlights the dual-path of modern data persistence: **Relational (PostgreSQL)** and **Document-oriented (MongoDB)**.

## 2. Mission

To equip developers with a comparative understanding of SQL vs. NoSQL architectures and provide a high-quality boilerplate for **Java 21/Spring Boot 3.5.x** ecosystems.

## 3. Target Users

*   **Developer:** Wants to compare how to map a Java object to a Table vs. a Document.
*   **Engineering Lead:** Needs to show when to use `Spring Data JPA` vs. `Spring Data MongoDB`.

## 4. MVP Scope

### ✅ In-Scope (MVP)

*   **Gradle Multi-Project Setup:** Centralized versioning via `libs.versions.toml`.
*   **SQL Module:** PostgreSQL implementation using JPA/Hibernate and Liquibase.
*   **NoSQL Module:** MongoDB implementation using Spring Data MongoDB.
*   **Testing Suite:** Integration tests using **Testcontainers** for both Postgres and MongoDB.
*   **Java Core:** Modern Java 21 syntax (Records, Pattern Matching).

### ❌ Out-of-Scope (Future)

*   Multi-database transactions (distributed transactions).
*   Graph databases (Neo4j) or Cache-specific modules (Redis).

## 5. User Stories

*   **As a Dev**, I want to see how the same Entity is modeled in a Relational DB vs. a Document DB so I can understand schema design.
*   **As a Dev**, I want to run integration tests that spin up a **Dockerized MongoDB** automatically so I don't have to install it locally.
*   **As a Lead**, I want the build to fail if the attempts to use SQL-specific patterns in the NoSQL module (and vice versa)./cond

## 6. Core Architecture

The repository is split by persistence strategy to allow for clear comparison.

*   **`persistence-sql/`**: Contains the JPA entities, Repositories, and Liquibase change-logs.
*   **`persistence-nosql/`**: Contains the MongoDB Documents and Repositories.
*   **`common-api/`**: Shared DTOs (Data Transfer Objects) and interfaces to ensure API consistency regardless of the backend.

## 7. Tools & Features

*   **Testcontainers:** Essential for the "No-Install" experience. It will manage both a `PostgreSQLContainer` and a `MongoDBContainer`.
*   **Database Migrations:** SQL uses **Liquibase**; NoSQL uses a custom `@Initializer` or **Mongock** for document migrations.
*   **Gradle Version Catalogs:** Unified dependency management for both database drivers.

## 8. Technology Stack

*   **Build Tool:** Gradle 9.x.x (Kotlin DSL).
*   **Language:** Java 21
*   **Framework:** Spring Boot 3.5.x
*   **Relational DB:** PostgreSQL 18+.
*   **NoSQL DB:** MongoDB 8+.
*   **Persistence Frameworks:** Spring Data JPA & Spring Data MongoDB.
*   **Testing:** JUnit 5, Testcontainers, AssertJ.

## 9. Security & Configuration

*   **Profiles:** Separate Spring profiles (`sql` vs `nosql`) to toggle different implementation beans if required, or separate sub-modules for total isolation.
*   **Validation:** Use of `jakarta.validation` (Hibernate Validator) at the service layer to keep validation logic database-agnostic.

## 10. API Specification

The toolbox will implement a **Product Catalog**.

| Feature | SQL Implementation | NoSQL Implementation |
| --- | --- | --- |
| **Storage** | Normalized tables (Product, Category, Supplier). | Nested Documents (Product with embedded Attributes). |
| **Query** | Complex Joins via JPQL/Criteria API. | Aggregation Pipeline / Template queries. |
| **Endpoint** | `/api/v1/sql/products` | `/api/v1/nosql/products` |

## 11. Success Criteria

1.  **Parity:** Both implementations must fulfill the same Functional Requirements.
2.  **Containerized Testing:** `./gradlew test` must successfully launch and teardown both DB containers.
3.  **Documentation:** A `COMPARISON.md` file explaining *why* a developer would choose one over the other for this specific use case.

## 12. Implementation Phases

*   **Phase 1:** Gradle 9 skeleton and shared `common-api` module.
*   **Phase 2:** PostgreSQL + JPA module with Testcontainers.
*   **Phase 3:** MongoDB module with Testcontainers.
*   **Phase 4:** Unified API layer and documentation.

## 13. Risks & Mitigations

| Risk | Mitigation |
| --- | --- |
| **Docker Overhead:** Running two containers simultaneously might be slow. | Configure Gradle to run tests in parallel or provide a toggle to test only one module at a time. |
| **Confusing Annotations:** Juniors mixing `@Entity` (JPA) and `@Document` (Mongo). | Strict package separation and clear "Best Practices" comments in the code. |
