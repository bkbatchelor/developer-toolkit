package com.developer.toolkit.nosql.repository;

import com.developer.toolkit.nosql.BaseIntegrationTest;
import com.developer.toolkit.nosql.entity.Attribute;
import com.developer.toolkit.nosql.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindProduct() {
        Product product = new Product();
        product.setName("Gaming Laptop");
        product.setPrice(new BigDecimal("1200.00"));
        product.setSku("TEST-SKU-1");
        product.setAttributes(List.of(new Attribute("brand", "Razer")));

        Product saved = productRepository.save(product);
        assertThat(saved.getId()).isNotNull();

        Product found = productRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Gaming Laptop");
    }

    @Test
    void shouldFindByNameContaining() {
        Product p1 = new Product();
        p1.setName("Apple iPhone");
        p1.setSku("TEST-SKU-2");
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Samsung Galaxy");
        p2.setSku("TEST-SKU-3");
        productRepository.save(p2);

        List<Product> results = productRepository.findByNameContainingIgnoreCase("Apple");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).contains("Apple");
    }

    @Test
    void shouldFindByCategoryName() {
        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setCategoryName("Computers");
        p1.setSku("TEST-SKU-4");
        productRepository.save(p1);

        List<Product> results = productRepository.findByCategoryName("Computers");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCategoryName()).isEqualTo("Computers");
    }

    @Test
    void shouldFindByPriceBetween() {
        Product p1 = new Product();
        p1.setName("Cheap");
        p1.setPrice(new BigDecimal("10.00"));
        p1.setSku("TEST-SKU-5");
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Expensive");
        p2.setPrice(new BigDecimal("100.00"));
        p2.setSku("TEST-SKU-6");
        productRepository.save(p2);

        List<Product> results = productRepository.findByPriceBetween(new BigDecimal("5.00"), new BigDecimal("50.00"));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Cheap");
    }
}
