package com.developer.toolkit.nosql.controller;

import com.developer.toolkit.nosql.dto.ProductResponseDTO;
import com.developer.toolkit.nosql.entity.Attribute;
import com.developer.toolkit.nosql.entity.Product;
import com.developer.toolkit.nosql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/nosql/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> searchProducts(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "minStock", required = false) Integer minStock,
            @RequestParam Map<String, String> allParams,
            Pageable pageable
    ) {
        Map<String, String> attributes = new HashMap<>();
        allParams.forEach((key, value) -> {
            if (key.startsWith("attr:")) {
                attributes.put(key.substring(5), value);
            }
        });

        Page<Product> products = productService.searchProducts(
                name, categoryName, minPrice, maxPrice, minStock, attributes, pageable);

        return products.map(this::mapToResponseDTO);
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        Map<String, String> attrMap = new HashMap<>();
        if (product.getAttributes() != null) {
            attrMap = product.getAttributes().stream()
                    .collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sku(product.getSku())
                .stockLevel(product.getStockLevel())
                .categoryName(product.getCategoryName())
                .supplierName(product.getSupplierName())
                .attributes(attrMap)
                .build();
    }
}
