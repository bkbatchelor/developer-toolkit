# Specification: Create the Gradle scaffolding to support a monorepo

## Overview
This track focuses on establishing the foundational Gradle scaffolding for a monorepo architecture. It aims to set up a multi-project build environment that supports the Developer Toolbox (DT) initiative, which compares SQL and NoSQL data persistence strategies.

## Functional Requirements
- **Multi-Project Setup:** The Gradle build must be configured to support a monorepo structure containing the following subprojects:
  - `persistence-common-api`: The shared API layer containing common interfaces and Data Transfer Objects (DTOs).
  - `persistence-sql`: The module for PostgreSQL implementation using JPA/Hibernate.
  - `persistence-nosql`: The module for MongoDB implementation using Spring Data MongoDB.
- **Dependency Management:** All project dependencies MUST be centralized using a Gradle Version Catalog (`gradle/libs.versions.toml`).
- **Build Scripts:** All Gradle build configuration files (e.g., `settings.gradle.kts`, `build.gradle.kts`) MUST be written using the Kotlin Domain-Specific Language (Kotlin DSL).

## Non-Functional Requirements
- **Maintainability:** The build scripts should be clean, modular, and easy to maintain as the project grows.
- **Consistency:** Dependency versions must be strictly managed via the version catalog to avoid conflicts across subprojects.
- **Compatibility:** The scaffolding must be compatible with Gradle 9.x.x, Java 21, and Spring Boot 3.5.x.

## Acceptance Criteria
- [ ] A root `settings.gradle.kts` file exists and correctly includes the three defined subprojects (`persistence-common-api`, `persistence-sql`, `persistence-nosql`).
- [ ] A root `build.gradle.kts` exists with common configuration for all subprojects (e.g., applying the Java/Spring Boot plugins, configuring repositories).
- [ ] A `gradle/libs.versions.toml` file exists and defines the core dependencies (Spring Boot, Java version, Testcontainers, Lombok, etc.).
- [ ] Each subproject has its own `build.gradle.kts` file referencing dependencies from the version catalog.
- [ ] Executing `./gradlew build` from the root directory completes successfully without errors.

## Out of Scope
- Implementation of the actual Java code, DTOs, or database repositories within the subprojects.
- Configuration of GitHub Actions CI/CD pipelines (this will be handled in a separate track).
- Detailed configuration of Testcontainers or Liquibase.