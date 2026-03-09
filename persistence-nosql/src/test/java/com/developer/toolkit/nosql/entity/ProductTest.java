package com.developer.toolkit.nosql.entity;

import com.developer.toolkit.nosql.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest extends BaseIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testProductMapping() {
        // Given
        Attribute color = new Attribute("color", "red");
        Attribute size = new Attribute("size", "XL");
        
        Product product = new Product();
        product.setId("prod-1");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setSku("SKU-123");
        product.setStockLevel(10);
        product.setCategoryName("Electronics");
        product.setSupplierName("Test Supplier");
        product.setAttributes(List.of(color, size));

        // When
        Product savedProduct = mongoTemplate.save(product);

        // Then
        Product foundProduct = mongoTemplate.findById(savedProduct.getId(), Product.class);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
        assertThat(foundProduct.getAttributes()).hasSize(2);
        assertThat(foundProduct.getAttributes().get(0).getKey()).isEqualTo("color");
        assertThat(foundProduct.getAttributes().get(0).getValue()).isEqualTo("red");
    }
}
