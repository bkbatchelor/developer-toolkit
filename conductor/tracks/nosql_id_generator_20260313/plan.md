# Implementation Plan: MongoDB UUIDv7 ID and Index Generator

## Phase 1: Project Setup & Module Initialization [checkpoint: 355889f]
- [x] Task: Create new Gradle module `persistence-nosql-id-generator`. [83b2fcc]
- [x] Task: Configure basic dependencies for Spring Data MongoDB and Testcontainers. [53873ea]
- [x] Task: Conductor - User Manual Verification 'Phase 1: Project Setup & Module Initialization' (Protocol in workflow.md) [355889f]

## Phase 2: UUIDv7 ID Generation Engine (TDD)
- [ ] Task: Write failing unit tests for 128-bit container layout and time-ordering properties of UUIDv7.
- [ ] Task: Implement core logic for UUIDv7 generation (Partition 1: Timestamp, Partition 2: Entropy) to pass tests.
- [ ] Task: Write failing unit tests for Base16 (Hexadecimal) encoding and validation.
- [ ] Task: Implement Base16 encoding logic to pass tests.
- [ ] Task: Provide a static utility class for manual ID generation.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: UUIDv7 ID Generation Engine (TDD)' (Protocol in workflow.md)

## Phase 3: Spring Data MongoDB Integration (TDD)
- [ ] Task: Write failing integration tests (using Testcontainers) for custom `@Id` generator and `BinData` storage conversion.
- [ ] Task: Implement a custom Spring Data MongoDB `IdentifierGenerator` for time-ordered IDs to pass tests.
- [ ] Task: Implement custom `ReadingConverter` and `WritingConverter` for Hexadecimal <-> `BinData` mapping to pass tests.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Spring Data MongoDB Integration (TDD)' (Protocol in workflow.md)

## Phase 4: Automated Index Management (TDD)
- [ ] Task: Write failing integration tests (using Testcontainers) for automated index discovery and application at startup.
- [ ] Task: Implement a mechanism to scan entities for Spring Data MongoDB index annotations to pass tests.
- [ ] Task: Develop an automated index initializer to ensure collection indexes are applied on startup to pass tests.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Automated Index Management (TDD)' (Protocol in workflow.md)

## Phase 5: Final Integration, Reliability & Security (TDD)
- [ ] Task: Write failing tests for "Query Buffer" strategy (NTP drift mitigation).
- [ ] Task: Implement "Query Buffer" logic to handle clock skew to pass tests.
- [ ] Task: Write ArchUnit rules to enforce "Hardened Signatures" and IDOR protection at the application level.
- [ ] Task: Perform end-to-end integration testing using Testcontainers for MongoDB 8+.
- [ ] Task: Conductor - User Manual Verification 'Phase 5: Final Integration, Reliability & Security (TDD)' (Protocol in workflow.md)