# Implementation Plan: PostgreSQL Implementation (SQL Module)

## Phase 1: Database Infrastructure & Entities [checkpoint: 65dce13]

- [x] Task: Configure PostgreSQL 18+ connection properties in `persistence-sql/src/main/resources/application.yml`. e5e2750
- [x] Task: Set up Liquibase in `persistence-sql/`. ee3ccc3
    - [x] Create `db/changelog/db.changelog-master.xml`.
    - [x] Create initial migration for `category`, `supplier`, and `product` tables with primary keys, foreign keys, and indexes.
- [x] Task: Implement `Category` entity. 29032cd
    - [x] Write failing unit test for `Category` mapping.
    - [x] Implement `Category` entity with standard JPA annotations.
- [x] Task: Implement `Supplier` entity. d86a56a
    - [x] Write failing unit test for `Supplier` mapping.
    - [x] Implement `Supplier` entity with standard JPA annotations.
- [x] Task: Implement `Product` entity. 9053dbd
    - [x] Write failing unit test for `Product` mapping and relationships (Category, Supplier).
    - [x] Implement `Product` entity with JPA annotations and proper relationship mapping.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Database Infrastructure & Entities' (Protocol in workflow.md) 65dce13

## Phase 2: Persistence Layer & Queries

- [x] Task: Create Spring Data JPA Repositories. 4c600f6
    - [x] Create `CategoryRepository`, `SupplierRepository`, and `ProductRepository`.
- [x] Task: Implement Product Search Queries using JPQL. 4c600f6
    - [x] Write failing integration tests for complex product searching (name, category, price range, stock).
    - [x] Define `@Query` methods in `ProductRepository` using JPQL to satisfy the search requirements.
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
