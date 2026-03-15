package com.developer.toolkit.nosql.idgenerator;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.stereotype.Component;

@Component
public class AutoIndexInitializer {

    private final MongoTemplate mongoTemplate;
    private final MongoMappingContext mongoMappingContext;

    public AutoIndexInitializer(MongoTemplate mongoTemplate, MongoMappingContext mongoMappingContext) {
        this.mongoTemplate = mongoTemplate;
        this.mongoMappingContext = mongoMappingContext;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        System.out.println("AutoIndexInitializer: initIndicesAfterStartup called!");
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

        for (MongoPersistentEntity<?> entity : mongoMappingContext.getPersistentEntities()) {
            System.out.println("AutoIndexInitializer: Processing entity: " + entity.getType().getName());
            IndexOperations indexOps = mongoTemplate.indexOps(entity.getType());
            resolver.resolveIndexFor(entity.getType()).forEach(idx -> {
                System.out.println("AutoIndexInitializer: Creating index: " + idx);
                indexOps.ensureIndex(idx);
            });
        }
    }
}
