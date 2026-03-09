package com.developer.toolkit.nosql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabasePropertiesTest extends BaseIntegrationTest {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Test
    void testMongoDBPropertiesAreInjected() {
        assertThat(mongoUri).isNotNull();
        assertThat(mongoUri).startsWith("mongodb://");
    }
}
