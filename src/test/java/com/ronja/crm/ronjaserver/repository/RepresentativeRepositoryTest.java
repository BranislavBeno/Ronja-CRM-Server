package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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
    Assertions.assertThat(result).hasSize(2);
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testSearchBy() {
    List<Representative> result = repository.findByCustomerId(1);
    Assertions.assertThat(result).hasSize(1);
    assertRepresentative(result.getFirst());
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testDeleteById() {
    repository.deleteById(1);
    Assertions.assertThat(repository.findAll()).hasSize(1);
  }

  @Test
  @Sql(scripts = {"/scripts/INIT_CUSTOMERS.sql", "/scripts/INIT_REPRESENTATIVES.sql"})
  void testSave() {
    Representative representative = createRepresentative(LocalDate.now());
    repository.save(representative);

    Assertions.assertThat(repository.findAll()).hasSize(3);
  }

  @Test
  void testFindScheduledForNextNDays() {
    IntStream.of(0, 7, 8).forEach(days -> {
      Representative representative = createRepresentative(LocalDate.now().plusDays(days));
      repository.save(representative);
    });

    List<Representative> result = repository.findScheduledForNextNDays(7);
    Assertions.assertThat(result).hasSize(2);
  }

  private void assertRepresentative(Representative representative) {
    Assertions.assertThat(representative.getId()).isEqualTo(1);
    Assertions.assertThat(representative.getFirstName()).isEqualTo("John");
    Assertions.assertThat(representative.getLastName()).isEqualTo("Doe");
    Assertions.assertThat(representative.getStatus()).isEqualTo("ACTIVE");
    Assertions.assertThat(representative.getNotice()).isEqualTo("nothing special");
    Assertions.assertThat(representative.getPosition()).isEqualTo("CEO");
    Assertions.assertThat(representative.getRegion()).isEqualTo("V4");
    Assertions.assertThat(representative.getContactType()).isEqualTo("MAIL");
    Assertions.assertThat(representative.getLastVisit()).isEqualTo(LocalDate.of(2020, 10, 7));
    Assertions.assertThat(representative.getScheduledVisit()).isEqualTo(LocalDate.of(2021, 4, 25));
    Assertions.assertThat(representative.getPhoneNumbers()).hasSize(2);
    Assertions.assertThat(representative.getEmails()).hasSize(1);
    Assertions.assertThat(representative.getCustomer()).isNotNull();
    Assertions.assertThat(representative.getCustomer().getId()).isEqualTo(1);
  }

  private Representative createRepresentative(LocalDate scheduledDate) {
    Representative representative = new Representative();
    representative.setFirstName("Charles");
    representative.setLastName("Smith");
    representative.setStatus("INACTIVE");
    representative.setNotice("");
    representative.setRegion("");
    representative.setPosition("");
    representative.setContactType("PERSONAL");
    representative.setLastVisit(LocalDate.now());
    representative.setScheduledVisit(scheduledDate);
    representative.setCustomer(createCustomer());
    representative.setPhoneNumbers(Collections.emptyList());
    representative.setEmails(Collections.emptyList());
    return representative;
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setCategory("LEVEL_1");
    customer.setFocus("MANUFACTURE");
    customer.setStatus("ACTIVE");

    return customer;
  }
}