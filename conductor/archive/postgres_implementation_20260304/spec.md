# Specification: PostgreSQL Implementation (SQL Module)

## Overview
Implement PostgreSQL 18+ integration within the `persistence-sql` module using Spring Data JPA and Hibernate. This track includes designing a normalized schema for e-commerce entities, setting up automated migrations with Liquibase, and exposing a robust REST API for product discovery.

## Functional Requirements
- **Data Modeling:**
    - Create `Product`, `Category`, and `Supplier` entities.
    - `Product` attributes: name, description, price, SKU, stock level.
    - `Category` attributes: name (flat structure).
    - `Supplier` attributes: name, contact info, address, lead times.
    - Establish relationships: `Product` belongs to one `Category` and has one `Supplier`.
- **Database Migrations:**
    - Set up Liquibase with a structured XML/YAML master changelog.
    - Define initial schema migrations for all entities and relationships.
- **Persistence Layer:**
    - Implement Spring Data JPA repositories for all entities.
    - Develop complex search queries using JPQL (filtering by name, category, supplier, price ranges, and stock availability).
- **API Layer:**
    - Expose `GET /api/v1/sql/products`.
    - Support pagination and sorting.
    - Provide full projections including related category and supplier data.

## Non-Functional Requirements
- **Performance:** Ensure efficient query execution with proper indexing (on SKU, category_id, etc.).
- **Maintainability:** Adhere to the established project coding standards and structured migration patterns.

## Acceptance Criteria
- [ ] Liquibase migrations successfully create the schema in PostgreSQL 18+.
- [ ] Entities are correctly mapped with JPA/Hibernate.
- [ ] JPQL queries correctly filter products based on multiple criteria.
- [ ] The `/api/v1/sql/products` endpoint returns paged results with associated entity data.
- [ ] Integration tests verify the end-to-end flow using Testcontainers.

## Out of Scope
- NoSQL/MongoDB implementation (handled in a separate track).
- Frontend UI development.
- Nested category hierarchies.
