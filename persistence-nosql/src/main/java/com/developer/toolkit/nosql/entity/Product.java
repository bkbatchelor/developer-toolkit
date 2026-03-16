package com.developer.toolkit.nosql.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {

    @Id
    @Field(targetType = FieldType.BINARY)
    private String id;

    private String name;

    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    private String sku;

    private Integer stockLevel;

    private String categoryName;

    private String supplierName;

    private List<Attribute> attributes;
}
