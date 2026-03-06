package com.developer.toolkit.sql.repository;

import com.developer.toolkit.sql.entity.BaseDataJpaTest;
import com.developer.toolkit.sql.entity.Category;
import com.developer.toolkit.sql.entity.Product;
import com.developer.toolkit.sql.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends BaseDataJpaTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Category electronics;
    private Category furniture;
    private Supplier supplierA;
    private Supplier supplierB;

    @BeforeEach
    void setUp() {
        electronics = new Category();
        electronics.setId(1L);
        electronics.setName("Electronics");
        entityManager.persist(electronics);

        furniture = new Category();
        furniture.setId(2L);
        furniture.setName("Furniture");
        entityManager.persist(furniture);

        supplierA = new Supplier();
        supplierA.setId(1L);
        supplierA.setName("Supplier A");
        entityManager.persist(supplierA);

        supplierB = new Supplier();
        supplierB.setId(2L);
        supplierB.setName("Supplier B");
        entityManager.persist(supplierB);

        createProduct(1L, "Laptop", "SKU-001", electronics, supplierA, new BigDecimal("1200.00"), 10);
        createProduct(2L, "Smartphone", "SKU-002", electronics, supplierB, new BigDecimal("800.00"), 25);
        createProduct(3L, "Desk", "SKU-003", furniture, supplierA, new BigDecimal("450.00"), 5);
        createProduct(4L, "Chair", "SKU-004", furniture, supplierB, new BigDecimal("150.00"), 50);

        entityManager.flush();
        entityManager.clear();
    }

    private void createProduct(Long id, String name, String sku, Category category, Supplier supplier, BigDecimal price, int stock) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setSku(sku);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setPrice(price);
        product.setStockLevel(stock);
        entityManager.persist(product);
    }

    @Test
    void shouldFindProductsByNameContaining() {
        var spec = ProductSpecifications.searchProducts("phone", null, null, null, null);
        List<Product> products = productRepository.findAll(spec);
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Smartphone");
    }

    @Test
    void shouldFindProductsByCategory() {
        var spec = ProductSpecifications.searchProducts(null, "Electronics", null, null, null);
        List<Product> products = productRepository.findAll(spec);
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Laptop", "Smartphone");
    }

    @Test
    void shouldFindProductsByPriceRange() {
        var spec = ProductSpecifications.searchProducts(null, null, new BigDecimal("400.00"), new BigDecimal("900.00"), null);
        List<Product> products = productRepository.findAll(spec);
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Smartphone", "Desk");
    }

    @Test
    void shouldFindProductsByMinStock() {
        var spec = ProductSpecifications.searchProducts(null, null, null, null, 20);
        List<Product> products = productRepository.findAll(spec);
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Smartphone", "Chair");
    }

    @Test
    void shouldFindProductsByCombinedCriteria() {
        var spec = ProductSpecifications.searchProducts("Desk", "Furniture", null, null, null);
        List<Product> products = productRepository.findAll(spec);
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Desk");
    }
}
