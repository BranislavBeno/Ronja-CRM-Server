package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Container
    static final MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0.25")
            .withDatabaseName("test")
            .withUsername("Jon")
            .withPassword("Doe")
            .withReuse(true);

    static {
        container.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    CustomerRepository cut;

    @Test
    @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
    void testFindAll() {
        List<Customer> result = (List<Customer>) cut.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
    void testSearchBy() {
        List<Customer> result = cut.findByCompanyNameContainsAllIgnoreCase("John");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getCategory()).isEqualTo(Category.LEVEL_1);
        assertThat(result.get(0).getFocus()).isEqualTo(Focus.TRADE);
        assertThat(result.get(0).getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(result.get(0).getCompanyName()).isEqualTo("JohnCorp");
    }

    @Test
    @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
    void testDeleteById() {
        cut.deleteById(1);
        assertThat(cut.findAll()).hasSize(1);
    }

    @Test
    @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
    void testSave() {
        Customer customer = new Customer();
        customer.setCategory(Category.LEVEL_1);
        customer.setFocus(Focus.BUILDER);
        customer.setStatus(Status.ACTIVE);
        customer.setCompanyName("NewmanCorp");
        cut.save(customer);

        assertThat(cut.findAll()).hasSize(3);
    }
}