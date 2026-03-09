package com.developer.toolkit.nosql.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDTOTest {

    @Test
    void testDtoMapping() {
        ProductResponseDTO dto = ProductResponseDTO.builder()
                .id("prod-1")
                .name("Test Product")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .sku("SKU-1")
                .stockLevel(10)
                .categoryName("Electronics")
                .supplierName("Supplier A")
                .attributes(Map.of("color", "red"))
                .build();

        assertThat(dto.getId()).isEqualTo("prod-1");
        assertThat(dto.getName()).isEqualTo("Test Product");
        assertThat(dto.getAttributes()).containsEntry("color", "red");
    }
}
