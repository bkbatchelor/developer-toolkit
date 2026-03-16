# NoSQL ID & Index Generator

A high-performance Java library specifically designed for Spring Data MongoDB applications. This module provides time-ordered UUIDv7 generation, optimal BSON binary storage conversion, and automated index management.

## 🚀 Key Features

- **UUIDv7 Generation:** High-performance, time-ordered unique identifiers following the UUIDv7 specification (48-bit timestamp, 80-bit entropy).
- **Optimal BSON Storage:** Seamless conversion between 32-character Hexadecimal strings in Java and efficient `BinData` in MongoDB, reducing index size and improving query performance.
- **Automated Index Initialization:** Discovery and application of Spring Data MongoDB index annotations (`@Indexed`, `@CompoundIndex`) at application startup.
- **Query Drift Mitigation:** "Query Buffer" utilities to handle NTP clock skew between distributed systems during range-based queries.
- **Architectural Security:** Integrated **ArchUnit** rules to enforce "Hardened Signatures" and prevent Insecure Direct Object Reference (IDOR) vulnerabilities.

## 🛠 Tech Stack

- **Java 21:** Modern language features for robust identifier handling.
- **Spring Data MongoDB:** Seamless integration with the Spring ecosystem.
- **MongoDB 8+:** Leverages modern BSON performance optimizations.
- **Testcontainers:** End-to-end verification using isolated MongoDB instances.
- **ArchUnit:** Automated architectural and security rule enforcement.

## 📦 Components

### `UuidV7Generator`
Core engine for generating 128-bit UUIDv7 identifiers. Supports both raw `byte[]` and Base16 (Hexadecimal) string formats.

### `UuidV7WritingConverter` & `UuidV7ReadingConverter`
Native Spring Data converters that bridge Java Strings and MongoDB Binary types. Uses a `GenericConverter` strategy to ensure ID conversion is targeted and safe for other string fields.

### `UuidV7IdGeneratorCallback`
A Spring Data `BeforeConvertCallback` and `BeforeSaveCallback` that automatically assigns and validates identifiers before they reach the database layer.

### `AutoIndexInitializer`
Listens for the `ApplicationReadyEvent` to scan and apply collection indexes, ensuring the database schema matches application requirements on startup.

### `UuidV7QueryUtils`
Utilities for extracting timestamps from IDs and generating "safe" range query boundaries with a configurable buffer (default 5s) to mitigate NTP drift.

## 💻 Usage

### Dependency Integration
Add the following to your `build.gradle.kts`:
```kotlin
implementation(project(":persistence-nosql-id-generator"))
```

### Entity Configuration
Annotate your `@Id` field with `@Field(targetType = FieldType.BINARY)` to activate optimal storage:
```java
@Document(collection = "products")
public class Product {
    @Id
    @Field(targetType = FieldType.BINARY)
    private String id;
    
    // ...
}
```

### Manual ID Generation
```java
String id = UuidV7Generator.generateAsString();
```

## 🧪 Testing

### Running Tests
Execute the comprehensive test suite (requires Docker for Testcontainers):
```bash
./gradlew :persistence-nosql-id-generator:test
```

### Security Auditing
The module includes ArchUnit rules to ensure:
1. DTOs do not expose raw `id` fields (use `productId` or similar).
2. Repositories do not use raw `Object` return types.
3. Controllers do not use raw `String` path variables for IDs.

## 📜 License
This module is part of the [Developer Toolkit](../README.md) and is licensed under the [MIT](../LICENSE) License.
