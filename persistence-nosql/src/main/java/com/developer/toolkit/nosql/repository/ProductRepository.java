package com.developer.toolkit.nosql.repository;

import com.developer.toolkit.nosql.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
