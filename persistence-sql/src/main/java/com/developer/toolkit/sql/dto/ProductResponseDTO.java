package com.developer.toolkit.sql.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Integer stockLevel;
    private String categoryName;
    private String supplierName;
}
