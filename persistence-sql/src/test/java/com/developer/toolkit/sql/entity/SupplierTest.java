package com.developer.toolkit.sql.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierTest extends BaseDataJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSupplierPersistence() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Acme Corp");
        supplier.setContactInfo("contact@acme.com");
        supplier.setAddress("123 Acme St, Acmeville");
        supplier.setLeadTimes("2 weeks");

        Supplier savedSupplier = entityManager.persistFlushFind(supplier);

        assertThat(savedSupplier.getId()).isEqualTo(1L);
        assertThat(savedSupplier.getName()).isEqualTo("Acme Corp");
        assertThat(savedSupplier.getContactInfo()).isEqualTo("contact@acme.com");
        assertThat(savedSupplier.getAddress()).isEqualTo("123 Acme St, Acmeville");
        assertThat(savedSupplier.getLeadTimes()).isEqualTo("2 weeks");
    }
}
