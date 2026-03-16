# Technology Stack

## Language & Frameworks
- **Java 21:** Utilizing modern language features like Records and Pattern Matching for concise and robust code.
- **Spring Boot 3.5.x:** Providing the foundational framework for building production-ready Java applications.
- **Gradle 9.x.x (Kotlin DSL):** Managing project dependencies and build processes with a flexible, expressive DSL.

## Data Persistence
- **PostgreSQL 18+:** Serving as our relational database choice for normalized data storage.
- **MongoDB 8+:** Providing a document-oriented approach for flexible, non-relational data modeling.
- **Spring Data JPA:** For seamless mapping and interaction with our SQL database using standard JPA/Hibernate.
- **Spring Data MongoDB:** For intuitive document-to-object mapping and interaction with our NoSQL database.
- **Liquibase:** Managing SQL database schema migrations to ensure consistency across environments.
- **UUIDv7:** Employing time-ordered, high-performance unique identifiers stored optimally as BSON BinData for MongoDB.

## Testing & Validation
- **JUnit 5 & AssertJ:** Our standard tools for writing expressive and robust unit and integration tests.
- **Testcontainers:** Essential for running isolated, containerized database instances during integration testing.
- **Jakarta Bean Validation:** Implementing standard annotations at the API layer for consistent data validation.
- **ArchUnit:** Enforcing architectural security rules, hardened signatures, and IDOR protection at the application level.

## Utilities & Deployment
- **Lombok:** Reducing boilerplate code for Java objects through powerful annotations.
- **GitHub Actions:** Automating our CI/CD pipelines to ensure continuous quality and delivery.
