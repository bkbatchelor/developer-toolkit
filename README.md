# Developer Toolkit (DT)

> A comparative analysis of SQL (PostgreSQL) and NoSQL (MongoDB) database architectures within a modern Java 21 and Spring Boot 3.5.x ecosystem.

## 🚀 Features

- **Comparative Architecture:** Side-by-side implementation of a Product Catalog using Relational (SQL) and Document (NoSQL) storage.
- **Modern Tech Stack:** Built with Java 21, Spring Boot 3.5.x, and Gradle 9.x.x.
- **Database Migrations:** Managed schema changes using Liquibase (SQL) and Mongock (NoSQL).
- **Advanced Testing:** Comprehensive integration test suite utilizing Testcontainers for isolated, containerized database instances.
- **API Layer:** RESTful APIs with Jakarta Bean Validation for consistent data integrity.
- **Monorepo Structure:** Cleanly separated modules for SQL and NoSQL persistence layers.

## 🛠 Tech Stack

- **Primary Language:** Java 21
- **Core Frameworks:** Spring Boot 3.5.x, Spring Data JPA, Spring Data MongoDB
- **Build Tool:** Gradle 9.x.x (Kotlin DSL)
- **Databases:** PostgreSQL 18+, MongoDB 8+
- **Migrations:** Liquibase, Mongock
- **Testing:** JUnit 5, AssertJ, Testcontainers
- **Utilities:** Lombok

## 📦 Modules

- **[persistence-sql](./persistence-sql)**: Implementation of a product catalog service using PostgreSQL.
  - **Key Technologies**: Spring Data JPA, Hibernate, Liquibase, PostgreSQL Driver.
  - **Functionality**: Normalized storage for products, categories, and suppliers with complex join support.
- **[persistence-nosql](./persistence-nosql)**: Implementation of a product catalog service using MongoDB.
  - **Key Technologies**: Spring Data MongoDB, Mongock.
  - **Functionality**: Document-oriented storage with nested attributes and dynamic filtering support.

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

Contributions are welcome! Please open an issue or submit a PR for any improvements or bug fixes.

## 📜 License

This project is licensed under the [MIT](LICENSE) License.
