# Specification: MongoDB UUIDv7 ID and Index Generator

## Overview
This track implements a high-performance Java library for MongoDB, specifically designed for Spring Data MongoDB applications. It focuses on UUIDv7 for time-ordered, BSON-optimized identifiers and provides automated index management to ensure database efficiency and consistency.

## Track ID
`nosql_id_generator_20260313`

## Functional Requirements

### 1. UUIDv7 ID Generation
- **Strategy:** Implement a strict 128-bit container layout:
    - Partition 1: Timestamp (bits 0-47, K-ordered).
    - Partition 2: Entropy (bits 48-127, using `SecureRandom`).
- **Encoding:** Support Base16 (Hexadecimal) string representation (32 characters).
- **Manual Generation:** Provide a static utility class for manual ID generation.

### 2. Spring Data MongoDB Integration
- **Custom @Id Generator:** Implement a custom identifier generator for Spring Data MongoDB's `@Id` annotation.
- **Conversion Logic:** Automatically convert 32-character Hexadecimal IDs to MongoDB `BinData` (Subtype) before storage. raw Hexadecimal string storage is prohibited.
- **Spring Converters:** Provide custom Spring Data `ReadingConverter` and `WritingConverter` for the `BinData` to Hexadecimal transition.

### 3. Automated Index Management
- **Auto-Apply Indexes:** Implement a mechanism to automatically apply indexes to the MongoDB collection at application startup based on Spring Data MongoDB annotations (`@Indexed`, `@CompoundIndex`).
- **Consistency:** Ensure index definitions are consistent across microservices by distributing the library as a JAR.

### 4. Reliability & Security
- **NTP Drift Mitigation:** Implement a "Query Buffer" strategy (up to 5 seconds skew) to handle clock drift.
- **IDOR Protection:** Ensure database IDs are not exposed to clients or in API URLs.
- **RLS Enforcement:** Mandate "Hardened Signatures" in repository methods (ArchUnit compliance).

## Non-Functional Requirements
- **Performance:** Minimize MongoDB BSON index fragmentation and page splits.
- **Ease of Use:** Seamless integration into existing Spring Boot projects.

## Acceptance Criteria
- [ ] Successful generation of UUIDv7 IDs with time-ordered properties.
- [ ] Automatic storage of IDs as `BinData` in MongoDB.
- [ ] Automated index creation at application startup based on entity annotations.
- [ ] Passing integration tests with Testcontainers.

## Out of Scope
- Support for Micronaut or other frameworks in the MVP.
- Generation of Java source code for indexes (refocused to auto-apply).