package com.developer.toolkit.nosql.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ProductResponseDTO {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Integer stockLevel;
    private String categoryName;
    private String supplierName;
    private Map<String, String> attributes;
}
