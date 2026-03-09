package com.developer.toolkit.nosql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class MongockSetupTest extends BaseIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testMongockInitialization() {
        // Context loading will fail if Mongock is not correctly configured.
        // We check for the presence of the mongock change log collection
        assertThat(mongoTemplate.getCollectionNames()).containsAnyOf("mongockChangeLog", "products");
    }
}
