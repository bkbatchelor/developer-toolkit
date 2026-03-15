package com.developer.toolkit.nosql.idgenerator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "indexed_entities")
@CompoundIndexes({
    @CompoundIndex(name = "name_category_idx", def = "{'name': 1, 'category': 1}")
})
public class IndexedEntity {

    @Id
    @Field(targetType = FieldType.BINARY)
    private String id;

    @Indexed(unique = true)
    private String uniqueField;

    private String name;

    private String category;

    public IndexedEntity() {
    }

    public IndexedEntity(String uniqueField, String name, String category) {
        this.uniqueField = uniqueField;
        this.name = name;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueField() {
        return uniqueField;
    }

    public void setUniqueField(String uniqueField) {
        this.uniqueField = uniqueField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
