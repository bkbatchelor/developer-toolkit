package com.developer.toolkit.sql.repository;

import com.developer.toolkit.sql.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> searchProducts(
            String name,
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if (StringUtils.hasText(name)) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(categoryName)) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.equal(root.get("category").get("name"), categoryName));
            }

            if (minPrice != null) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minStock != null) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.greaterThanOrEqualTo(root.get("stockLevel"), minStock));
            }

            return predicate;
        };
    }
}
