package com.developer.toolkit.nosql.idgenerator;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(classes = TestApplication.class)
public abstract class BaseIdGeneratorIntegrationTest {

    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}
