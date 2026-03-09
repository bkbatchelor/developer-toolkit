package com.developer.toolkit.nosql.service;

import com.developer.toolkit.nosql.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MongoTemplate mongoTemplate;

    public Page<Product> searchProducts(
            String name,
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            Map<String, String> attributes,
            Pageable pageable
    ) {
        Query query = new Query();

        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i"));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.addCriteria(Criteria.where("categoryName").is(categoryName));
        }
        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = Criteria.where("price");
            if (minPrice != null) {
                priceCriteria.gte(minPrice);
            }
            if (maxPrice != null) {
                priceCriteria.lte(maxPrice);
            }
            query.addCriteria(priceCriteria);
        }
        if (minStock != null) {
            query.addCriteria(Criteria.where("stockLevel").gte(minStock));
        }
        if (attributes != null && !attributes.isEmpty()) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                query.addCriteria(Criteria.where("attributes").elemMatch(
                        Criteria.where("key").is(entry.getKey())
                                .and("value").is(entry.getValue())
                ));
            }
        }

        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class);
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);

        return new PageImpl<>(products, pageable, total);
    }
}
