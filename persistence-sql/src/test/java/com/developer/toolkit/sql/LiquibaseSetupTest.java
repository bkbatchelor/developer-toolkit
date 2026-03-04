package com.developer.toolkit.sql;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class LiquibaseSetupTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private DataSource dataSource;

    @Test
    void testLiquibaseBeanIsConfigured() {
        assertThat(context.containsBean("liquibase")).isTrue();
        SpringLiquibase liquibase = context.getBean(SpringLiquibase.class);
        assertThat(liquibase.getChangeLog()).isEqualTo("classpath:db/changelog/db.changelog-master.xml");
    }
}
