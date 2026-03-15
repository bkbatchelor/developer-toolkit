package com.developer.toolkit.nosql.idgenerator;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AutoIndexIntegrationTest extends BaseIdGeneratorIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void shouldAutomaticallyApplyIndexesOnStartup() {
        // Retrieve all indexes for the "indexed_entities" collection
        List<Document> indexInfo = new ArrayList<>();
        mongoTemplate.getCollection("indexed_entities").listIndexes().into(indexInfo);

        // Extract index names
        List<String> indexNames = indexInfo.stream()
                .map(doc -> doc.getString("name"))
                .toList();

        // 1. Default _id index
        assertThat(indexNames).contains("_id_");

        // 2. @Indexed(unique = true) index on "uniqueField"
        assertThat(indexNames).contains("uniqueField_1");
        
        Optional<Document> uniqueFieldIndex = indexInfo.stream()
                .filter(doc -> "uniqueField_1".equals(doc.getString("name")))
                .findFirst();
        assertThat(uniqueFieldIndex).isPresent();
        assertThat(uniqueFieldIndex.get().getBoolean("unique")).isTrue();

        // 3. @CompoundIndex on "name" and "category"
        assertThat(indexNames).contains("name_category_idx");
        
        Optional<Document> compoundIndex = indexInfo.stream()
                .filter(doc -> "name_category_idx".equals(doc.getString("name")))
                .findFirst();
        assertThat(compoundIndex).isPresent();
        Document keyDoc = compoundIndex.get().get("key", Document.class);
        assertThat(keyDoc).isNotNull();
        assertThat(keyDoc.get("name")).isEqualTo(1);
        assertThat(keyDoc.get("category")).isEqualTo(1);
    }
}
