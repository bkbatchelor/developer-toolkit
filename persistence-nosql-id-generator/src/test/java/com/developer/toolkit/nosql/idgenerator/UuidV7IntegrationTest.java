package com.developer.toolkit.nosql.idgenerator;

import org.bson.Document;
import org.bson.types.Binary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UuidV7IntegrationTest extends BaseIdGeneratorIntegrationTest {

    @Autowired
    private TestEntityRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldGenerateIdOnSaveAndStoreAsBinData() {
        TestEntity entity = new TestEntity("Automated ID");
        TestEntity savedEntity = repository.save(entity);

        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getId()).hasSize(32);
        assertThat(savedEntity.getId()).matches("^[0-9a-f]{32}$");

        // Verify it was stored as BinData in MongoDB, not as a String
        Document rawDoc = mongoTemplate.getCollection("test_entities")
                .find()
                .first();

        assertThat(rawDoc).isNotNull();
        Object rawId = rawDoc.get("_id");
        assertThat(rawId).isInstanceOf(Binary.class);
        
        // Ensure that the BinData matches the encoded string
        Binary binId = (Binary) rawId;
        String encodedRaw = Base16Codec.encode(binId.getData());
        assertThat(encodedRaw).isEqualTo(savedEntity.getId());
    }

    @Test
    void shouldRespectManuallySetValidId() {
        String manualId = UuidV7Generator.generateAsString();
        TestEntity entity = new TestEntity(manualId, "Manual ID");
        TestEntity savedEntity = repository.save(entity);

        assertThat(savedEntity.getId()).isEqualTo(manualId);

        Optional<TestEntity> retrievedEntity = repository.findById(manualId);
        assertThat(retrievedEntity).isPresent();
        assertThat(retrievedEntity.get().getName()).isEqualTo("Manual ID");
    }

    @Test
    void shouldThrowExceptionWhenSavingWithInvalidHexLength() {
        TestEntity entity = new TestEntity("123", "Invalid Length ID");
        
        assertThatThrownBy(() -> repository.save(entity))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasStackTraceContaining("Hex string must have an even length");
    }

    @Test
    void shouldThrowExceptionWhenSavingWithInvalidHexCharacters() {
        TestEntity entity = new TestEntity("00ff1234abcd5678901234567890123G", "Invalid Char ID");

        assertThatThrownBy(() -> repository.save(entity))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasStackTraceContaining("Invalid hexadecimal character");
    }

    @Test
    void shouldRetrieveCorrectlyAfterSavingMultipleEntities() {
        TestEntity e1 = repository.save(new TestEntity("Entity 1"));
        TestEntity e2 = repository.save(new TestEntity("Entity 2"));
        TestEntity e3 = repository.save(new TestEntity("Entity 3"));

        assertThat(repository.count()).isEqualTo(3);
        
        assertThat(repository.findById(e1.getId())).isPresent().get().extracting(TestEntity::getName).isEqualTo("Entity 1");
        assertThat(repository.findById(e2.getId())).isPresent().get().extracting(TestEntity::getName).isEqualTo("Entity 2");
        assertThat(repository.findById(e3.getId())).isPresent().get().extracting(TestEntity::getName).isEqualTo("Entity 3");
    }
}
