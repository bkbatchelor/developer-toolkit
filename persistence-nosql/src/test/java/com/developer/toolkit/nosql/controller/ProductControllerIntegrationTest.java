package com.developer.toolkit.nosql.controller;

import com.developer.toolkit.nosql.BaseIntegrationTest;
import com.developer.toolkit.nosql.entity.Attribute;
import com.developer.toolkit.nosql.entity.Product;
import com.developer.toolkit.nosql.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        Product p1 = new Product();
        p1.setName("Gaming Laptop XYZ");
        p1.setCategoryName("Computers");
        p1.setPrice(new BigDecimal("1500.00"));
        p1.setStockLevel(10);
        p1.setSku("SKU-001");
        p1.setAttributes(List.of(new Attribute("brand", "Razer")));
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Office Mouse");
        p2.setCategoryName("Accessories");
        p2.setPrice(new BigDecimal("25.00"));
        p2.setStockLevel(100);
        p2.setSku("SKU-002");
        p2.setAttributes(List.of(new Attribute("brand", "Logitech")));
        productRepository.save(p2);
    }

    @Test
    void shouldSearchProducts() throws Exception {
        mockMvc.perform(get("/api/v1/nosql/products")
                .param("name", "Laptop")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Gaming Laptop XYZ"))
                .andExpect(jsonPath("$.content[0].attributes.brand").value("Razer"));
    }

    @Test
    void shouldFilterByAttribute() throws Exception {
        mockMvc.perform(get("/api/v1/nosql/products")
                .param("attr:brand", "Logitech")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Office Mouse"));
    }

    @Test
    void shouldSupportPaginationAndSorting() throws Exception {
        mockMvc.perform(get("/api/v1/nosql/products")
                .param("page", "0")
                .param("size", "1")
                .param("sort", "price,desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Gaming Laptop XYZ"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
}
