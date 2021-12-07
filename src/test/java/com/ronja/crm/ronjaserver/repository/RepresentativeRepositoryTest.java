package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepresentativeRepositoryTest extends BaseRepositoryTest {

  @Autowired
  RepresentativeRepository repository;

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testFindAll() {
    List<Representative> result = (List<Representative>) repository.findAll();
    assertThat(result).hasSize(2);
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testSearchBy() {
    List<Representative> result = repository.findByCustomerId(1);
    assertThat(result).hasSize(1);
    Representative representative = result.get(0);
    assertThat(representative.getId()).isEqualTo(1);
    assertThat(representative.getFirstName()).isEqualTo("John");
    assertThat(representative.getLastName()).isEqualTo("Doe");
    assertThat(representative.getStatus()).isEqualTo("ACTIVE");
    assertThat(representative.getNotice()).isEqualTo("nothing special");
    assertThat(representative.getPosition()).isEqualTo("CEO");
    assertThat(representative.getRegion()).isEqualTo("V4");
    assertThat(representative.getContactType()).isEqualTo("MAIL");
    assertThat(representative.getLastVisit()).isEqualTo(LocalDate.of(2020, 10, 7));
    assertThat(representative.getScheduledVisit()).isEqualTo(LocalDate.of(2021, 4, 25));
    assertThat(representative.getPhoneNumbers()).hasSize(2);
    assertThat(representative.getEmails()).hasSize(1);
    assertThat(representative.getCustomer()).isNotNull();
    assertThat(representative.getCustomer().getId()).isEqualTo(1);
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testDeleteById() {
    repository.deleteById(1);
    assertThat(repository.findAll()).hasSize(1);
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testSave() {
    Representative representative = new Representative();
    representative.setFirstName("Charles");
    representative.setLastName("Smith");
    representative.setStatus("INACTIVE");
    representative.setNotice("");
    representative.setRegion("");
    representative.setPosition("");
    representative.setContactType("PERSONAL");
    representative.setLastVisit(LocalDate.now());
    representative.setScheduledVisit(LocalDate.now());
    representative.setCustomer(provideCustomer());
    representative.setPhoneNumbers(Collections.emptyList());
    representative.setEmails(Collections.emptyList());
    repository.save(representative);

    assertThat(repository.findAll()).hasSize(3);
  }

  private Customer provideCustomer() {
    Customer customer = new Customer();
    customer.setCategory("LEVEL_1");
    customer.setFocus("MANUFACTURE");
    customer.setStatus("ACTIVE");

    return customer;
  }
}