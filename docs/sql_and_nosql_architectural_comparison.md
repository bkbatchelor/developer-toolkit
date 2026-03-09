# Comparative Analysis: SQL vs NoSQL Architectures

Based on the implementation within the `developer-toolkit` monorepo, here is a comparative analysis of the SQL (PostgreSQL) and NoSQL (MongoDB) architectures, focusing on how they solve the same domain problem (Product Management) differently.

## 1. Domain Modeling & Data Structure

*   **SQL (Normalization):** The PostgreSQL implementation strictly follows relational normalization. The `Product` entity uses `@ManyToOne` relationships to link to separate `Category` and `Supplier` tables. 
    *   *Strength:* Data consistency is guaranteed. Renaming a category or supplier only requires updating one row in a separate table.
    *   *Weakness:* The schema is rigid. If a "Gaming Laptop" needs a `gpu_model` property but a "T-Shirt" needs a `fabric_type`, you must either add nullable columns for every possible attribute, use a complex EAV (Entity-Attribute-Value) schema, or utilize Postgres-specific `JSONB` features (which sidesteps standard ORM mapping).
*   **NoSQL (Aggregate Documents):** The MongoDB implementation uses an aggregate document model. The `Product` document directly embeds `categoryName` and `supplierName` as strings, optimizing for read performance by eliminating joins. 
    *   *Strength:* Extreme flexibility. The `Product` document embeds a `List<Attribute>` (key-value pairs). This allows the application to store wildly different data shapes in the same collection without any schema migrations.
    *   *Weakness:* Data duplication. If a supplier changes their name, every single product document associated with them must be updated, increasing the risk of data anomalies if updates fail midway.

## 2. Advanced Search & Querying

Both architectures implemented a dynamic search endpoint allowing filtering by name, category, price ranges, and, critically, custom attributes.

*   **SQL (JPA Criteria API):** The dynamic search is handled via `ProductSpecifications` using the JPA Criteria API. 
    *   *Analysis:* The Criteria API is highly type-safe but notoriously verbose. Filtering by category requires traversing the relationship (`root.get("category").get("name")`). Filtering by dynamic attributes was omitted in the SQL implementation because doing so elegantly via an EAV table using JPA Specifications requires incredibly complex sub-queries and joins that destroy performance.
*   **NoSQL (MongoTemplate):** The search is handled using `MongoTemplate` and the MongoDB `Criteria` object.
    *   *Analysis:* The query construction is much more fluent and readable. Crucially, MongoDB excels at the dynamic attribute search. The query `query.addCriteria(Criteria.where("attributes").elemMatch(...))` allows the database to natively and efficiently search inside the embedded arrays, solving the exact problem that makes the SQL architecture rigid.

## 3. Migrations and Metadata Management

*   **SQL (Liquibase):** Liquibase executes XML/SQL changelogs to create rigid table structures, primary keys, and foreign key constraints *before* the application is allowed to boot. If the code expects a column that the database doesn't have, the application fails violently.
*   **NoSQL (Mongock):** Because MongoDB is "schema-less," Mongock isn't creating tables. Instead, it is used to manage **metadata and performance structures**. In this project, Mongock was used to define crucial indexes (e.g., ensuring `sku` is unique and indexing `price` for fast sorting) and to insert seed data. The database will happily accept a document with a missing field; the structure is enforced by the Java class, not the database engine.

## 4. Type Handling Friction

A notable difference occurred when handling currency (`price`).

*   **SQL:** Mapping Java's `BigDecimal` to a Postgres `NUMERIC` or `DECIMAL` column is a solved problem. The JPA provider (Hibernate) and the JDBC driver handle it seamlessly out of the box.
*   **NoSQL:** MongoDB stores data as BSON. While BSON supports a `Decimal128` type, mapping Spring Data's `BigDecimal` required explicit configuration. We had to annotate the field with `@Field(targetType = FieldType.DECIMAL128)` and register explicit `MongoCustomConversions` (Writing and Reading converters) to ensure precise, lossless financial calculations, showing that bridging complex Java types to document stores can sometimes require manual intervention. 

## Summary
The **SQL** module is built for strict data integrity and structured, predictable reporting where the shape of a "Product" rarely changes. The **NoSQL** module is built for flexibility and read performance, easily accommodating an e-commerce catalog where a television and a pair of shoes share very few physical attributes but must exist in the same catalog API.