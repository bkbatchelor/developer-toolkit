package com.developer.toolkit.nosql.idgenerator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndexedEntityRepository extends MongoRepository<IndexedEntity, String> {
}
