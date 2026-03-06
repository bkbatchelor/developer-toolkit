package com.developer.toolkit.sql.controller;

import com.developer.toolkit.sql.BaseIntegrationTest;
import com.developer.toolkit.sql.entity.Category;
import com.developer.toolkit.sql.entity.Product;
import com.developer.toolkit.sql.entity.Supplier;
import com.developer.toolkit.sql.repository.CategoryRepository;
import com.developer.toolkit.sql.repository.ProductRepository;
import com.developer.toolkit.sql.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        supplierRepository.deleteAll();

        Category electronics = new Category();
        electronics.setId(1L);
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Supplier supplierA = new Supplier();
        supplierA.setId(1L);
        supplierA.setName("Supplier A");
        supplierRepository.save(supplierA);

        createProduct(1L, "Laptop", "SKU-001", electronics, supplierA, new BigDecimal("1200.00"), 10);
        createProduct(2L, "Smartphone", "SKU-002", electronics, supplierA, new BigDecimal("800.00"), 25);
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
        productRepository.save(product);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/sql/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
                .andExpect(jsonPath("$[0].supplierName").value("Supplier A"));
    }

    @Test
    void shouldSearchProductsByName() throws Exception {
        mockMvc.perform(get("/api/v1/sql/products")
                .param("name", "phone")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Smartphone"));
    }

    @Test
    void shouldFilterProductsByPriceRange() throws Exception {
        mockMvc.perform(get("/api/v1/sql/products")
                .param("minPrice", "1000.00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }
}
