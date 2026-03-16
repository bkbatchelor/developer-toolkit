package com.developer.toolkit.nosql.idgenerator;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

@Component
public class UuidV7IdGeneratorCallback implements BeforeConvertCallback<Object>, BeforeSaveCallback<Object> {

    private final MongoMappingContext mappingContext;

    public UuidV7IdGeneratorCallback(MongoMappingContext mappingContext) {
        this.mappingContext = mappingContext;
    }

    @Override
    public Object onBeforeConvert(Object entity, String collection) {
        MongoPersistentEntity<?> persistentEntity = mappingContext.getPersistentEntity(entity.getClass());
        if (persistentEntity != null && persistentEntity.hasIdProperty()) {
            MongoPersistentProperty idProperty = persistentEntity.getIdProperty();
            if (idProperty != null && idProperty.getType().equals(String.class)) {
                PersistentPropertyAccessor<Object> accessor = persistentEntity.getPropertyAccessor(entity);
                Object id = accessor.getProperty(idProperty);
                if (id == null || ((String) id).isEmpty()) {
                    accessor.setProperty(idProperty, UuidV7Generator.generateAsString());
                }
            }
        }
        return entity;
    }

    @Override
    public Object onBeforeSave(Object entity, Document document, String collection) {
        if (document != null && document.containsKey("_id")) {
            Object idObj = document.get("_id");
            if (idObj instanceof String) {
                String strId = (String) idObj;
                if (strId.length() != 32 || !strId.matches("^[0-9a-fA-F]{32}$")) {
                    throw new IllegalArgumentException("Invalid Hexadecimal UUIDv7 String");
                }
                document.put("_id", new Binary(BsonBinarySubType.UUID_STANDARD, Base16Codec.decode(strId)));
            }
        }
        return entity;
    }
}
