package com.developer.toolkit.sql.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductResponseDTOTest {

    @Test
    void shouldBuildProductResponseDTO() {
        ProductResponseDTO dto = ProductResponseDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .sku("SKU-123")
                .stockLevel(100)
                .categoryName("Electronics")
                .supplierName("Supplier ABC")
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test Product");
        assertThat(dto.getDescription()).isEqualTo("Test Description");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("99.99"));
        assertThat(dto.getSku()).isEqualTo("SKU-123");
        assertThat(dto.getStockLevel()).isEqualTo(100);
        assertThat(dto.getCategoryName()).isEqualTo("Electronics");
        assertThat(dto.getSupplierName()).isEqualTo("Supplier ABC");
    }
}
