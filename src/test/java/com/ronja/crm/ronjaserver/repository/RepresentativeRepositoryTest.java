package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Representative;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepresentativeRepositoryTest {

  @Container
  static final MySQLContainer<?> container = new MySQLContainer<>("mysql:5.7.29")
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
  RepresentativeRepository cut;

  @Test
  @Sql(scripts = "/scripts/INIT_REPRESENTATIVES.sql")
  void testFindAll() {
    List<Representative> result = (List<Representative>) cut.findAll();
    assertThat(result).hasSize(2);
  }

  @Test
  @Sql(scripts = "/scripts/INIT_REPRESENTATIVES.sql")
  void testSearchBy() {
    List<Representative> result = cut.findByFirstNameContainsOrLastNameContainsAllIgnoreCase("John", "Doe");
    assertThat(result).hasSize(1);
    Representative representative = result.get(0);
    assertThat(representative.getId()).isEqualTo(1);
    assertThat(representative.getFirstName()).isEqualTo("John");
    assertThat(representative.getLastName()).isEqualTo("Doe");
    assertThat(representative.getStatus()).isEqualTo(Status.ACTIVE);
    assertThat(representative.getNotice()).isEqualTo("nothing special");
    assertThat(representative.getPosition()).isEqualTo("CEO");
    assertThat(representative.getRegion()).isEqualTo("V4");
    assertThat(representative.getLastVisit()).isEqualTo(LocalDate.of(2020, 10, 7));
    assertThat(representative.getScheduledVisit()).isEqualTo(LocalDate.of(2021, 4, 25));
  }

  @Test
  @Sql(scripts = "/scripts/INIT_REPRESENTATIVES.sql")
  void testDeleteById() {
    cut.deleteById(1);
    assertThat(cut.findAll()).hasSize(1);
  }

  @Test
  @Sql(scripts = "/scripts/INIT_REPRESENTATIVES.sql")
  void testSave() {
    Representative representative = new Representative();
    representative.setFirstName("Charles");
    representative.setLastName("Smith");
    representative.setStatus(Status.INACTIVE);
    representative.setNotice("");
    representative.setRegion("");
    representative.setPosition("");
    representative.setLastVisit(LocalDate.now());
    representative.setScheduledVisit(LocalDate.now());
    cut.save(representative);

    assertThat(cut.findAll()).hasSize(3);
  }
}