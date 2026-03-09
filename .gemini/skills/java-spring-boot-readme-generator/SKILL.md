---
name: java-spring-boot-readme-generator
description: Analyzes Java Spring Boot and Gradle source code to generate or update professional README.md files. Use this skill whenever a user needs to 'document the project', 'scan for readme info', or 'generate a setup guide' for any Java repository. It specializes in extracting tech stacks, API endpoints, and test details from local files. Do NOT use for general Java coding, debugging, or project bootstrapping.
---

# Spring Boot README Generator

This skill automates the creation of a comprehensive `README.md` file for Java Spring Boot and Gradle projects by analyzing the local codebase.

## Step 1: Analyze the Codebase

1.  Identify the root directories of all modules in the project. Look for directories containing `build.gradle` and `src/` directories (`src/main/java/`, `src/main/resources/`, `src/test/java/`, `src/test/resources/`).
2.  **Respect Git Ignore**: When scanning the codebase, you MUST ensure you respect `.gitignore` rules and ignore any unversioned files or directories (like `build/`, `.gradle/`, `.idea/`). Use tools that filter by `.gitignore` (e.g. `glob` with `respect_git_ignore: true`).
3.  Analyze `build.gradle` to extract the tech stack (Spring Boot version, Java version, dependencies).
4.  Analyze `src/main/java/` to understand the core functionality, domain models, and API endpoints (look for `@RestController`, `@RequestMapping`, etc.).
5.  Analyze `src/main/resources/` (e.g., `application.yml`, `application.properties`) to understand configuration and environment variables required.
6.  Analyze `src/test/java/` to list tests, determining their type (Unit, Integration) and purpose.

## Step 2: Draft the README

For each module containing source code (multi-module projects will have multiple READMEs, placed in the module's root), draft a `README.md` following this structure:

### Target Template

# [Project or Module Name]

> A brief description of what this module/project does based on your analysis of the codebase.

## Tech Stack
*   Java [Version]
*   Spring Boot [Version]
*   Gradle [Version]
*   [Dependency 1] [Version]
*   [Dependency 2] [Version]
*   [Dependency 3] [Version]
*(List each key dependency on a separate line with its version, e.g., PostgreSQL 15.0, Redis 7.0)*

## Prerequisites
*   Java [Version]
*   [Any other prerequisites like Docker 24.0, specific databases]

## Getting Started
*(Ensure all bash commands are enclosed inside of a code block)*
### Build
```bash
./gradlew build
```
### Run
```bash
./gradlew bootRun
```

## Environment Variables / Configuration
| Variable | Description | Default |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | Database connection string | `jdbc:postgresql://localhost:5432/db` |
*(Populate based on application.properties/yml analysis)*

## API Endpoints
| Verb | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/resource` | Retrieves a list of resources. |
| `POST` | `/api/v1/resource` | Creates a new resource. |
*(Populate based on Controller analysis)*

## Testing
*(Populate based on src/test/java/ analysis. Group the test types together. If there are more than 10 total test entries, separate the types into individual tables, e.g., one table for Unit Tests, one for Integration Tests.)*

| Name | Type | Description |
| :--- | :--- | :--- |
| `UserServiceTest` | Unit | Verifies user creation and validation logic. |
| `UserControllerIT` | Integration | Tests the end-to-end user registration flow. |

## Constraints
*   [List any technical constraints, required versions, or limitations discovered in the codebase or configurations.]

## Step 3: Present Preview

Once the draft(s) are ready, **output a preview of the README content directly to the user.**
Do not write the file(s) to disk yet.

Ask the user: "Does this README look good, or are there any changes you'd like to make before I save it?"

## Step 4: Write the File

Only **after the user approves** the preview, overwrite the existing `README.md` (or create a new one) in the respective module's root directory where the `src/` directory is located.