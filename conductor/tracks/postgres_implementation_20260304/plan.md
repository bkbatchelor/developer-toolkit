# Implementation Plan: PostgreSQL Implementation (SQL Module)

## Phase 1: Database Infrastructure & Entities

- [x] Task: Configure PostgreSQL 18+ connection properties in `persistence-sql/src/main/resources/application.yml`. e5e2750
- [x] Task: Set up Liquibase in `persistence-sql/`. ee3ccc3
    - [x] Create `db/changelog/db.changelog-master.xml`.
    - [x] Create initial migration for `category`, `supplier`, and `product` tables with primary keys, foreign keys, and indexes.
- [~] Task: Implement `Category` entity.
    - [ ] Write failing unit test for `Category` mapping.
    - [ ] Implement `Category` entity with standard JPA annotations.
- [ ] Task: Implement `Supplier` entity.
    - [ ] Write failing unit test for `Supplier` mapping.
    - [ ] Implement `Supplier` entity with standard JPA annotations.
- [ ] Task: Implement `Product` entity.
    - [ ] Write failing unit test for `Product` mapping and relationships (Category, Supplier).
    - [ ] Implement `Product` entity with JPA annotations and proper relationship mapping.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Database Infrastructure & Entities' (Protocol in workflow.md)

## Phase 2: Persistence Layer & Queries

- [ ] Task: Create Spring Data JPA Repositories.
    - [ ] Create `CategoryRepository`, `SupplierRepository`, and `ProductRepository`.
- [ ] Task: Implement Product Search Queries using JPQL.
    - [ ] Write failing integration tests for complex product searching (name, category, price range, stock).
    - [ ] Define `@Query` methods in `ProductRepository` using JPQL to satisfy the search requirements.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Persistence Layer & Queries' (Protocol in workflow.md)

## Phase 3: API Layer

- [ ] Task: Define Data Transfer Objects (DTOs) for Product projections.
    - [ ] Create `ProductResponseDTO` to include category and supplier names.
- [ ] Task: Implement `ProductController`.
    - [ ] Write failing integration tests for `GET /api/v1/sql/products` with various filter parameters.
    - [ ] Implement the controller mapping, service logic (if needed), and DTO mapping.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: API Layer' (Protocol in workflow.md)

## Phase 4: Final Integration & Verification

- [ ] Task: Perform end-to-end verification using Testcontainers.
    - [ ] Ensure all migrations run successfully on a real PostgreSQL container.
    - [ ] Verify full CRUD and search operations via API.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Final Integration & Verification' (Protocol in workflow.md)
