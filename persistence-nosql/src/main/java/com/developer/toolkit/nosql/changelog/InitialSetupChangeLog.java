package com.developer.toolkit.nosql.changelog;

import com.developer.toolkit.nosql.entity.Attribute;
import com.developer.toolkit.nosql.entity.Product;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

@ChangeUnit(id = "init-schema", order = "001", author = "developer")
public class InitialSetupChangeLog {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // Create Indexes
        IndexOperations indexOps = mongoTemplate.indexOps(Product.class);
        indexOps.ensureIndex(new Index().on("sku", Sort.Direction.ASC).unique());
        indexOps.ensureIndex(new Index().on("name", Sort.Direction.ASC));
        indexOps.ensureIndex(new Index().on("categoryName", Sort.Direction.ASC));
        indexOps.ensureIndex(new Index().on("price", Sort.Direction.ASC));

        // Seed Data
        Product laptop = new Product();
        laptop.setName("High-End Gaming Laptop");
        laptop.setDescription("A powerful laptop for gaming and professional work.");
        laptop.setPrice(new BigDecimal("2500.00"));
        laptop.setSku("LAP-001");
        laptop.setStockLevel(50);
        laptop.setCategoryName("Electronics");
        laptop.setSupplierName("TechCorp");
        laptop.setAttributes(List.of(
            new Attribute("processor", "Intel i9"),
            new Attribute("ram", "32GB"),
            new Attribute("gpu", "RTX 4080")
        ));

        Product smartphone = new Product();
        smartphone.setName("Pro Smartphone");
        smartphone.setDescription("The latest flagship smartphone.");
        smartphone.setPrice(new BigDecimal("999.99"));
        smartphone.setSku("PHN-001");
        smartphone.setStockLevel(150);
        smartphone.setCategoryName("Electronics");
        smartphone.setSupplierName("MobileSystems");
        smartphone.setAttributes(List.of(
            new Attribute("screen", "6.7 inch OLED"),
            new Attribute("storage", "256GB"),
            new Attribute("camera", "50MP")
        ));

        mongoTemplate.insertAll(List.of(laptop, smartphone));
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Product.class);
    }
}
