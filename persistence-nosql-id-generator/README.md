# persistence-nosql-id-generator

> A high-performance Java library for Spring Data MongoDB providing time-ordered UUIDv7 generation, BSON binary storage conversion, and automated index management.

## Documentation
* [SQL and NoSQL Architectural Comparison](../docs/sql-nosql-db/sql_and_nosql_architectural_comparison.md)

## Tech Stack
*   Java 21
*   Spring Boot 3.5.x
*   Spring Data MongoDB
*   MongoDB 8+ (Driver managed by Spring Boot)
*   Gradle 9.x.x
*   Testcontainers 1.21.4
*   ArchUnit 1.3.0

## Prerequisites
*   Java 21
*   Docker 24.0+ (Required for integration tests via Testcontainers)

## Getting Started

### Build
```bash
./gradlew :persistence-nosql-id-generator:build
```
### Test
```bash
./gradlew :persistence-nosql-id-generator:test
```

## Features & Components
| Component | Description |
| :--- | :--- |
| `UuidV7Generator` | Core engine for generating 128-bit UUIDv7 identifiers. |
| `UuidV7WritingConverter` | Converts Java Strings to MongoDB Binary types. |
| `UuidV7ReadingConverter` | Converts MongoDB Binary types to Java Strings. |
| `UuidV7IdGeneratorCallback` | Automatically assigns and validates identifiers before they reach the database layer. |
| `AutoIndexInitializer` | Discovers and applies Spring Data MongoDB index annotations at application startup. |
| `UuidV7QueryUtils` | Extracts timestamps from IDs and generates range query boundaries with a configurable buffer. |

## Usage Configuration
Add the following to your `build.gradle.kts`:
```kotlin
implementation(project(":persistence-nosql-id-generator"))
```
Annotate your `@Id` field with `@Field(targetType = FieldType.BINARY)` to activate optimal storage:
```java
@Document(collection = "products")
public class Product {
    @Id
    @Field(targetType = FieldType.BINARY)
    private String id;
}
```

## Testing

| Name of Method | Name of Class | Type | Description |
| :--- | :--- | :--- | :--- |
| `shouldGenerate16ByteId` | `UuidV7GeneratorTest` | Unit | Verifies UUIDv7 generator produces a 16-byte array. |
| `shouldContainCurrentTimestampInFirst48Bits` | `UuidV7GeneratorTest` | Unit | Validates the timestamp partition of the UUIDv7 generator. |
| `shouldBeTimeOrdered` | `UuidV7GeneratorTest` | Unit | Validates that generated IDs are strictly time-ordered. |
| `shouldGenerate32CharacterHexId` | `UuidV7GeneratorTest` | Unit | Validates Base16 encoded string representation. |
| `shouldEncodeBytesToHex` | `Base16CodecTest` | Unit | Tests Base16 encoding. |
| `shouldDecodeHexToBytes` | `Base16CodecTest` | Unit | Tests Base16 decoding. |
| `shouldExtractTimestampFromUuidV7String` | `UuidV7QueryUtilsTest` | Unit | Verifies timestamp extraction from UUIDv7 hex string. |
| `shouldCalculateSafeQueryTimestampWithBuffer` | `UuidV7QueryUtilsTest` | Unit | Tests safe query timestamp calculation. |
| `shouldCreateSafeQueryIdForTimestamp` | `UuidV7QueryUtilsTest` | Unit | Verifies generation of safe query IDs. |
| `repository_methods_should_have_hardened_signatures` | `SecurityArchUnitTest` | Architecture | Enforces repository methods have specific return types. |
| `controller_methods_should_not_expose_raw_id_in_path_variables` | `SecurityArchUnitTest` | Architecture | Prevents raw String IDs in PathVariables (IDOR protection). |
| `dtos_should_not_expose_raw_id_field` | `SecurityArchUnitTest` | Architecture | Prevents DTOs from exposing raw 'id' fields. |
| `shouldGenerateIdOnSaveAndStoreAsBinData` | `UuidV7IntegrationTest` | Integration | Verifies ID generation and BinData storage conversion on save. |
| `shouldRespectManuallySetValidId` | `UuidV7IntegrationTest` | Integration | Validates that manually set valid UUIDv7 IDs are preserved. |
| `shouldThrowExceptionWhenSavingWithInvalidHexLength` | `UuidV7IntegrationTest` | Integration | Ensures validation exception is thrown for invalid ID lengths. |
| `shouldThrowExceptionWhenSavingWithInvalidHexCharacters` | `UuidV7IntegrationTest` | Integration | Ensures validation exception is thrown for invalid ID characters. |
| `shouldAutomaticallyApplyIndexesOnStartup` | `AutoIndexIntegrationTest` | Integration | Validates automatic discovery and application of index annotations. |

## Constraints
*   Requires a running Docker daemon to execute the integration test suite (Testcontainers).
