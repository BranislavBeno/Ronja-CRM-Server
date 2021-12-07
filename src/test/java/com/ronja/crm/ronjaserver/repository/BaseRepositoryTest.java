package com.ronja.crm.ronjaserver.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

abstract class BaseRepositoryTest {

  static final MySQLContainer<?> CONTAINER_DB = new MySQLContainer<>("mysql:8.0.25")
      .withDatabaseName("test")
      .withUsername("Jon")
      .withPassword("Doe")
      .withReuse(true);

  static {
    CONTAINER_DB.start();
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", CONTAINER_DB::getJdbcUrl);
    registry.add("spring.datasource.password", CONTAINER_DB::getPassword);
    registry.add("spring.datasource.username", CONTAINER_DB::getUsername);
  }
}
