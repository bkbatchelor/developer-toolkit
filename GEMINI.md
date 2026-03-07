# Project Overview

The Developer Toolkit is a Gradle monorepo designed to provide a comparative analysis of SQL (PostgreSQL) and NoSQL (MongoDB) database architectures within a modern Java and Spring Boot ecosystem. 

**Key Technologies:**
*   **Language:** Java 21
*   **Framework:** Spring Boot 3.5.x
*   **Build Tool:** Gradle 9.x.x
*   **Databases:** PostgreSQL, MongoDB
*   **Testing:** JUnit Jupiter, AssertJ, Testcontainers

**Core Modules:**
*   `persistence-sql`: A Spring Boot application utilizing Spring Data JPA, Hibernate, and Liquibase to interact with a PostgreSQL database.
*   `persistence-nosql`: A Spring Boot application utilizing Spring Data MongoDB to interact with a MongoDB database.
*   `conductor`: Contains project documentation, product guidelines, tech stack details, and the project workflow definitions.
*   `.agents`: Contains definitions and skills for AI agents.

# Building and Running

The project utilizes the Gradle Wrapper to ensure consistent builds.

*   **Build the Project:**
    ```bash
    ./gradlew build
    ```
*   **Run Tests:**
    ```bash
    ./gradlew test
    ```
    *Note: The test suite uses Testcontainers, which requires a running Docker daemon to spin up ephemeral PostgreSQL and MongoDB instances.*

# Development Conventions

This project enforces strict development workflows and testing standards, managed via the `conductor` documentation.

*   **Task Management:** All work must be tracked and managed sequentially in the relevant `plan.md` file (found within `conductor/tracks/`).
*   **Test-Driven Development (TDD):** A Red-Green-Refactor cycle is mandatory. Write failing unit tests before implementing any application code.
*   **Code Coverage:** Maintain >80% code coverage for all modules.
*   **Tech Stack Adherence:** The technology stack is deliberate. Any deviations must be documented in `conductor/tech-stack.md` *before* implementation begins.
*   **Commit Guidelines:** Use Conventional Commits formatting (e.g., `feat(ui): add button`, `fix(auth): resolve token issue`).
*   **Task Verification:** Upon completing a phase, developers must execute a strict Phase Completion Verification and Checkpointing Protocol, which includes verifying test coverage, presenting a manual verification plan, and attaching auditable reports using `git notes`.