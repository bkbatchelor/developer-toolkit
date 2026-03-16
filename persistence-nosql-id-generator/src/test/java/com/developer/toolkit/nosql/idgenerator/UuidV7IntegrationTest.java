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
        TestEntity entity = new TestEntity();
        TestEntity savedEntity = repository.save(entity);

        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getId()).hasSize(32);
        assertThat(savedEntity.getId()).matches("^[0-9a-f]{32}$");

        Document rawDoc = mongoTemplate.getCollection("test_entities")
                .find()
                .first();

        assertThat(rawDoc).isNotNull();
        Object rawId = rawDoc.get("_id");
        assertThat(rawId).isInstanceOf(Binary.class);
        
        Binary binId = (Binary) rawId;
        String encodedRaw = Base16Codec.encode(binId.getData());
        assertThat(encodedRaw).isEqualTo(savedEntity.getId());
    }

    @Test
    void shouldRespectManuallySetValidId() {
        String manualId = UuidV7Generator.generateAsString();
        TestEntity entity = new TestEntity(manualId);
        TestEntity savedEntity = repository.save(entity);

        assertThat(savedEntity.getId()).isEqualTo(manualId);

        Optional<TestEntity> retrievedEntity = repository.findById(manualId);
        assertThat(retrievedEntity).isPresent();
    }

    @Test
    void shouldThrowExceptionWhenSavingWithInvalidHexLength() {
        TestEntity entity = new TestEntity("123");
        
        assertThatThrownBy(() -> repository.save(entity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Hexadecimal");
    }

    @Test
    void shouldThrowExceptionWhenSavingWithInvalidHexCharacters() {
        TestEntity entity = new TestEntity("00ff1234abcd5678901234567890123G");

        assertThatThrownBy(() -> repository.save(entity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Hexadecimal");
    }
}
