package com.developer.toolkit.sql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestApplication.class, properties = {
    "spring.liquibase.enabled=false",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@ActiveProfiles("test")
public class DatabasePropertiesTest {

    @Value("${spring.datasource.url:#{null}}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:#{null}}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:#{null}}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name:#{null}}")
    private String driverClassName;

    @Value("${spring.jpa.hibernate.ddl-auto:#{null}}")
    private String ddlAuto;

    @Test
    void testPostgresPropertiesAreConfigured() {
        assertThat(datasourceUrl).isNotNull().startsWith("jdbc:postgresql://");
        assertThat(datasourceUsername).isNotNull();
        assertThat(datasourcePassword).isNotNull();
        assertThat(driverClassName).isEqualTo("org.postgresql.Driver");
        assertThat(ddlAuto).isEqualTo("validate");
    }
}