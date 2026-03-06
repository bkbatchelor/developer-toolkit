# Developer Toolkit

> [!NOTE]
> This project is a work in progress.

The Developer Toolkit is a monorepo designed to provide a comparative analysis of SQL (PostgreSQL) and NoSQL (MongoDB) database architectures within a modern Java and Spring Boot ecosystem. It serves as a practical guide and boilerplate for developers and engineering leads to understand the trade-offs and benefits of each approach.

## ✨ Features

*   **Gradle Monorepo**: A multi-project setup using Gradle 9.x.x, with centralized dependency management.
*   **SQL vs. NoSQL**: Parallel implementations of a product catalog service using PostgreSQL and MongoDB.
*   **Java 21 & Spring Boot 3.5.x**: Built on the latest Java and Spring Boot versions.
*   **Testing**: Integration tests using Testcontainers for both databases.
*   **Validation**: Jakarta Bean Validation for robust data validation.
*   **CI/CD**: Automated builds and tests using GitHub Actions.

## 🚀 Modules

*   `persistence-sql`: A Spring Boot application that uses Spring Data JPA, Hibernate, and Liquibase to interact with a PostgreSQL database.
*   `persistence-nosql`: A Spring Boot application that uses Spring Data MongoDB to interact with a MongoDB database.
*   `conductor`: Project documentation, including product guidelines, tech stack, and workflow.
*   `.agents`: Contains definitions for AI agents that can work on this project.

## 🏁 Getting Started

This project is not yet runnable, but you can explore the code to see how the different persistence strategies are implemented.

## 🤝 Contributing

Contributions are welcome! Please feel free to open an issue or submit a pull request.
