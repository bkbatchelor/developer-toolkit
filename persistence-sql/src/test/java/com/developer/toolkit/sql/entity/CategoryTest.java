package com.developer.toolkit.sql.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest extends BaseDataJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCategoryPersistence() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Category savedCategory = entityManager.persistFlushFind(category);

        assertThat(savedCategory.getId()).isEqualTo(1L);
        assertThat(savedCategory.getName()).isEqualTo("Electronics");
    }
}
