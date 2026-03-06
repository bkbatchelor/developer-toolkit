package com.developer.toolkit.sql.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest extends BaseDataJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testProductPersistence() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        entityManager.persistAndFlush(category);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Acme Corp");
        entityManager.persistAndFlush(supplier);

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("High performance laptop");
        product.setPrice(new BigDecimal("1200.00"));
        product.setSku("LPT-001");
        product.setStockLevel(50);
        product.setCategory(category);
        product.setSupplier(supplier);

        Product savedProduct = entityManager.persistFlushFind(product);

        assertThat(savedProduct.getId()).isEqualTo(1L);
        assertThat(savedProduct.getName()).isEqualTo("Laptop");
        assertThat(savedProduct.getDescription()).isEqualTo("High performance laptop");
        assertThat(savedProduct.getPrice()).isEqualByComparingTo("1200.00");
        assertThat(savedProduct.getSku()).isEqualTo("LPT-001");
        assertThat(savedProduct.getStockLevel()).isEqualTo(50);
        
        assertThat(savedProduct.getCategory()).isNotNull();
        assertThat(savedProduct.getCategory().getName()).isEqualTo("Electronics");
        
        assertThat(savedProduct.getSupplier()).isNotNull();
        assertThat(savedProduct.getSupplier().getName()).isEqualTo("Acme Corp");
    }
}
