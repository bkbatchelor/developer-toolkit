package com.developer.toolkit.sql.service;

import com.developer.toolkit.sql.dto.ProductResponseDTO;
import com.developer.toolkit.sql.entity.Product;
import com.developer.toolkit.sql.repository.ProductRepository;
import com.developer.toolkit.sql.repository.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDTO> searchProducts(
            String name,
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock
    ) {
        Specification<Product> spec = ProductSpecifications.searchProducts(
                name, categoryName, minPrice, maxPrice, minStock);
        
        return productRepository.findAll(spec).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sku(product.getSku())
                .stockLevel(product.getStockLevel())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .supplierName(product.getSupplier() != null ? product.getSupplier().getName() : null)
                .build();
    }
}
