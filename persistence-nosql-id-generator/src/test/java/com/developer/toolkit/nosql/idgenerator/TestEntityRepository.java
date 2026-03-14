package com.developer.toolkit.nosql.idgenerator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestEntityRepository extends MongoRepository<TestEntity, String> {
}
