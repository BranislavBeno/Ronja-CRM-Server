package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends BaseRepositoryTest {

  @Autowired
  CustomerRepository repository;

  @Test
  @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
  void testFindAll() {
    List<Customer> result = (List<Customer>) repository.findAll();
    assertThat(result).hasSize(2);
  }

  @Test
  @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
  void testSearchBy() {
    List<Customer> result = repository.findByCompanyNameContainsAllIgnoreCase("John");
    assertThat(result).hasSize(1);
    assertThat(result.getFirst().getId()).isEqualTo(1);
    assertThat(result.getFirst().getCategory()).isEqualTo("LEVEL_1");
    assertThat(result.getFirst().getFocus()).isEqualTo("TRADE");
    assertThat(result.getFirst().getStatus()).isEqualTo("ACTIVE");
    assertThat(result.getFirst().getCompanyName()).isEqualTo("JohnCorp");
  }

  @Test
  @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
  void testDeleteById() {
    repository.deleteById(1);
    assertThat(repository.findAll()).hasSize(1);
  }

  @Test
  @Sql(scripts = "/scripts/INIT_CUSTOMERS.sql")
  void testSave() {
    Customer customer = new Customer();
    customer.setCategory("LEVEL_1");
    customer.setFocus("BUILDER");
    customer.setStatus("ACTIVE");
    customer.setCompanyName("NewmanCorp");
    repository.save(customer);

    assertThat(repository.findAll()).hasSize(3);
  }
}