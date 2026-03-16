# Developer Toolkit (DT)

> A comparative analysis of SQL (PostgreSQL) and NoSQL (MongoDB) database architectures within a modern Java 21 and Spring Boot 3.5.x ecosystem.

## 🚀 Features

- **Comparative Architecture:** Side-by-side implementation of a Product Catalog using Relational (SQL) and Document (NoSQL) storage.
- **Modern Tech Stack:** Built with Java 21 (Records, Pattern Matching), Spring Boot 3.5.x, and Gradle 9.x.x.
- **Optimized NoSQL Identifiers:** Custom UUIDv7 generator for time-ordered, high-performance IDs stored as efficient BSON `BinData`.
- **Database Migrations:** Managed schema and data changes using Liquibase (SQL) and Mongock (NoSQL).
- **Automated Indexing:** Startup-time index discovery and application for MongoDB based on entity annotations.
- **Advanced Testing:** 
  - **Integration:** Comprehensive suite utilizing **Testcontainers** for isolated, containerized database instances (PostgreSQL 18+, MongoDB 8+).
  - **Architecture:** **ArchUnit** rules to enforce "Hardened Signatures" and IDOR protection at the application level.
- **Monorepo Structure:** Cleanly separated modules for persistence, utilities, and infrastructure.

## 🛠 Tech Stack

- **Primary Language:** Java 21
- **Core Frameworks:** Spring Boot 3.5.x, Spring Data JPA, Spring Data MongoDB
- **Build Tool:** Gradle 9.x.x (Kotlin DSL)
- **Databases:** PostgreSQL 18+, MongoDB 8+
- **Migrations:** Liquibase, Mongock
- **Testing:** JUnit 5, AssertJ, Testcontainers, ArchUnit
- **Utilities:** Lombok, UUIDv7

## 📦 Modules

- **[persistence-sql](./persistence-sql)**: Implementation of a product catalog service using PostgreSQL.
  - **Key Technologies**: Spring Data JPA, Hibernate, Liquibase, PostgreSQL Driver.
  - **Functionality**: Normalized storage for products, categories, and suppliers with complex join support.
- **[persistence-nosql](./persistence-nosql)**: Implementation of a product catalog service using MongoDB.
  - **Key Technologies**: Spring Data MongoDB, Mongock.
  - **Functionality**: Document-oriented storage with nested attributes, dynamic filtering, and UUIDv7 integration.
- **[persistence-nosql-id-generator](./persistence-nosql-id-generator)**: High-performance library for MongoDB ID and index management.
  - **Key Technologies**: Spring Data MongoDB, BSON, ArchUnit.
  - **Functionality**: UUIDv7 generation, Hex-to-Binary converters, and automated index initialization.

## 📚 Documentation

The `docs/sql-nosql-db` folder contains various materials comparing SQL and NoSQL architectures:

| File Name | File Type | Description |
| :--- | :--- | :--- |
| `database_comparison_ guide.png` | Image | Visual guide comparing database architectures |
| `intro_to_databases_slide_deck.pptx` | Presentation | Introductory slide deck on databases |
| `sql_vs_nosql_video.mp4` | Video | Visual introduction to database concepts |
| `sql_vs_nosql_podcast.m4a` | Audio | Audio discussion on Postgres, MongoDB, and Testcontainers |
| `sql_and_nosql_architectural_comparison.md` | Markdown Document | Detailed architectural comparison of SQL and NoSQL databases |

## 💻 Installation

1. **Prerequisites**
   - Java 21 JDK
   - Docker 24.0+ (Required for integration tests via Testcontainers)

2. **Clone the repository**
   ```bash
   git clone https://github.com/bkbatchelor/developer-toolkit.git
   cd developer-toolkit
   ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

## 🏃 Usage

### Running Subprojects
You can run each persistence module independently:

**SQL Module:**
```bash
./gradlew :persistence-sql:bootRun
```

**NoSQL Module:**
```bash
./gradlew :persistence-nosql:bootRun
```

### Running Tests
To execute the full test suite (requires Docker):
```bash
./gradlew test
```

## 🤝 Contributing

Contributions are welcome! Please follow the TDD workflow defined in `conductor/workflow.md` and use conventional commits.

## 📜 License

This project is licensed under the [MIT](LICENSE) License.
