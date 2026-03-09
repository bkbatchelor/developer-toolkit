package com.developer.toolkit.nosql.service;

import com.developer.toolkit.nosql.BaseIntegrationTest;
import com.developer.toolkit.nosql.entity.Attribute;
import com.developer.toolkit.nosql.entity.Product;
import com.developer.toolkit.nosql.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest extends BaseIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        // Seed data
        Product p1 = new Product();
        p1.setName("Gaming Laptop XYZ");
        p1.setCategoryName("Computers");
        p1.setPrice(new BigDecimal("1500.00"));
        p1.setStockLevel(10);
        p1.setAttributes(List.of(
                new Attribute("brand", "Razer"),
                new Attribute("ram", "32GB")
        ));
        p1.setSku("GL-XYZ-1");

        Product p2 = new Product();
        p2.setName("Office Laptop ABC");
        p2.setCategoryName("Computers");
        p2.setPrice(new BigDecimal("800.00"));
        p2.setStockLevel(50);
        p2.setAttributes(List.of(
                new Attribute("brand", "Dell"),
                new Attribute("ram", "16GB")
        ));
        p2.setSku("OL-ABC-2");

        Product p3 = new Product();
        p3.setName("Gaming Mouse");
        p3.setCategoryName("Accessories");
        p3.setPrice(new BigDecimal("100.00"));
        p3.setStockLevel(5);
        p3.setAttributes(List.of(
                new Attribute("brand", "Razer"),
                new Attribute("wireless", "true")
        ));
        p3.setSku("GM-3");

        productRepository.saveAll(List.of(p1, p2, p3));
    }

    @Test
    void searchByName() {
        Page<Product> result = productService.searchProducts("Laptop", null, null, null, null, null, PageRequest.of(0, 10));
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting("name").containsExactlyInAnyOrder("Gaming Laptop XYZ", "Office Laptop ABC");
    }

    @Test
    void searchByCategory() {
        Page<Product> result = productService.searchProducts(null, "Accessories", null, null, null, null, PageRequest.of(0, 10));
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Gaming Mouse");
    }

    @Test
    void searchByPriceRange() {
        Page<Product> result = productService.searchProducts(null, null, new BigDecimal("500.00"), new BigDecimal("1000.00"), null, null, PageRequest.of(0, 10));
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Office Laptop ABC");
    }

    @Test
    void searchByStockLevel() {
        Page<Product> result = productService.searchProducts(null, null, null, null, 10, null, PageRequest.of(0, 10));
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2); // 10 and 50
    }

    @Test
    void searchByAttributes() {
        Map<String, String> attrs = Map.of("brand", "Razer");
        Page<Product> result = productService.searchProducts(null, null, null, null, null, attrs, PageRequest.of(0, 10));
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting("name").containsExactlyInAnyOrder("Gaming Laptop XYZ", "Gaming Mouse");

        Map<String, String> attrs2 = Map.of("ram", "32GB");
        Page<Product> result2 = productService.searchProducts(null, null, null, null, null, attrs2, PageRequest.of(0, 10));
        assertThat(result2).isNotNull();
        assertThat(result2.getTotalElements()).isEqualTo(1);
        assertThat(result2.getContent().get(0).getName()).isEqualTo("Gaming Laptop XYZ");
    }

    @Test
    void searchWithPaginationAndSorting() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "price"));
        Page<Product> result = productService.searchProducts(null, "Computers", null, null, null, null, pageRequest);
        
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Gaming Laptop XYZ"); // highest price
    }
}
