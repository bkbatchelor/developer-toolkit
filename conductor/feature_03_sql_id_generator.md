ID Generator that generates secondary indexes for PostgreSQL

# Product Guide: PostgreSQL Native ID & Index Architect

## Product Vision

To leverage the native **UUIDv7** capabilities of PostgreSQL 18 to eliminate the "random UUID tax"—specifically B-tree index fragmentation and write amplification—while maintaining 128-bit global uniqueness and distributed generation safety.

## Core Features

* **Native UUIDv7 Generation:** Utilizes the built-in `uuidv7()` function, which is implemented at the C-level for maximum throughput (~21.7K ops/sec).
* **Engine-Level Monotonicity:** PostgreSQL’s implementation uses a 12-bit sub-millisecond fraction as a counter to ensure monotonic ordering even if the system clock skews backwards or multiple IDs are generated in the same millisecond.
* **Zero-Overhead Metadata Extraction:** Built-in support for `uuid_extract_timestamp()` and `uuid_extract_version()` allows for high-performance temporal queries without storing redundant `created_at` columns.

### ID Generator Strategy

| Component | Specification | Detail |
| --- | --- | --- |
| **Data Type** | `UUID` | 16-byte binary native type. |
| **Bit Layout** | RFC 9562 Compliant | 48-bit Timestamp + 12-bit Sub-ms Fraction + 62-bit Entropy. |
| **Entropy Source** | `SecureRandom` / CSPNG | Leverages system-level entropy for the 62-bit "rand_b" partition. |
| **Collision Resist.** | ~4.6 Quintillion | 62 bits of entropy provide a collision-free space for most enterprise scales. |

### Storage & Persistence

* **Binary Integrity:** Unlike MongoDB, which requires conversion to `BinData`, PostgreSQL stores the `UUID` type natively as a 128-bit binary value.
* **Index Efficiency:** Uses standard B-tree indexes. Because UUIDv7 is time-ordered, new entries append to the "right" of the tree, maintaining high page fill factors and preventing the ~35% index bloat common with UUIDv4.
* **Generated Columns:** Can be used with PostgreSQL 18’s new `VIRTUAL` generated columns to derive IDs or labels at query time without additional disk usage.

### Reliability & Security

* **Clock Skew Handling:** The `uuidv7(interval)` overload allows manual adjustments for historical backfilling or future-dating, while the internal 12-bit counter mitigates micro-skew.
* **IDOR Protection:** IDs are strictly internal. PostgreSQL RLS (Row-Level Security) must be used to ensure `owner_id` is validated alongside the UUID for every query.
* **Auditing:** Use the new `RETURNING old.*, new.*` syntax in PostgreSQL 18 to capture ID transitions during `UPDATE` or `UPSERT` operations atomically.

---

## Technical Problem Statement

Random UUIDs (v4) cause "index thrashing" in PostgreSQL, where random inserts force constant page splits and cache misses. PostgreSQL 18's native UUIDv7 implementation provides a "sequential-like" insert performance (identical to `BIGINT` auto-increment) while preserving the decentralized benefits of a 128-bit identifier.
