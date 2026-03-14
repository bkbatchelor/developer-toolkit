package com.developer.toolkit.nosql.idgenerator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "test_entities")
public class TestEntity {

    @Id
    @Field(targetType = FieldType.BINARY)
    private String id;

    public TestEntity() {
    }

    public TestEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
